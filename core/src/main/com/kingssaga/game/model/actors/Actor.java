package com.kingssaga.game.model.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.actions.TakeDamageAction;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.weapons.Weapon;
import com.kingssaga.game.model.factories.GraphicsFactory;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public abstract class Actor implements IActor {
    protected int health;
    protected int maxHealth;

    protected float speed;
    protected int acceleration;

    protected Sprite sprite;
    protected Body body;

    protected float width;
    protected float height;

    protected Weapon wieldedWeapon;
    protected boolean isFacingLeft;

    protected int attackDelay;
    protected int takeDamageDelay;

    protected List<String> hitSounds;

    protected SoundPlayer sp;

    protected List<Item> inventory;

    protected FactoryManager fm;


    @Override
    public void takeDamage(TakeDamageAction attackAction) {
        hitSound();
        takeDamageColor();
        attackAction.execute();
        takeDamageDelay = 3;
    }

    private void hitSound() {
        int randInt = new Random().nextInt(hitSounds.size());
        sp.playSound(hitSounds.get(randInt));
    }

    @Override
    public boolean isWithinRange(Vector2 target, Vector2 attackRange) {
        Vector2 distance = distanceTo(target);
        return distance.x <= attackRange.x && distance.y <= attackRange.y;
    }

    /**
     * Calculates the distance between the actor and the specified target.
     *
     * @param target The target position.
     * @return The distance between the actor and the target.
     */
    protected Vector2 distanceTo(Vector2 target) {
        float deltaX = abs(target.x - this.getPosition().x);
        float deltaY = abs(target.y - this.getPosition().y);
        return new Vector2(deltaX, deltaY);
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        if (validateHealth(health)) {
            this.health = Math.min(health, maxHealth);
        }
        }

    /**
     * Validates that a health value is within the valid range.
     * 
     * @param health The health value to validate.
     */
    private boolean validateHealth(int health) {
        return health >= 0 && health <= Constants.MAX_HEALTH;
    }

    @Override
    public void restoreHealth(int healthRestored) {
        int newHealth = this.health + healthRestored;
        if(validateHealth(healthRestored) && validateHealth(newHealth)) {
            setHealth(Math.min(newHealth, this.maxHealth));
        }
    }

    @Override
    public boolean isAlive() {
        return !(health == 0);
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(int speed) {
        if (speed >= 0 && speed <= Constants.MAX_SPEED) {
            this.speed = speed;
        }
    }

    @Override
    public Vector2 getPosition() {
        if (body == null) {
            throw new NullPointerException("Position is null");
        } else {
            return body.getPosition();
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        if (position.x < 0 || position.y < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        } else {
            body.setTransform(position, body.getAngle());
        }
    }

    @Override
    public float getZ() {
        return -(body.getPosition().y - sprite.getHeight() / 2);
    }

    /**
     * Gets the body of the actor.
     *
     * @return The body of the actor.
     */
    @Override
    public Body getBody() {
        if (this.body == null) {
            throw new NullPointerException("Body is null");
        } else {
            return this.body;
        }
    }

    @Override
    public float getWidth() {
        return sprite.getWidth();
    }

    @Override
    public float getHeight() {
        return sprite.getHeight();
    }


    /**
     * Draws the actor on the screen.
     *
     * @param batch The sprite batch to draw the actor with.
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (wieldedWeapon.isAttacking()) {
            wieldedWeapon.drawAttack(batch);
        }
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        body.getWorld().destroyBody(body);
    }

    /**
     * Flips the sprite horizontally.
     */
    protected void flip() {
        sprite.flip(true, false);
        wieldedWeapon.flipAttacks();
        isFacingLeft = !isFacingLeft;
    }

    /**
     * Creates a sprite from a file.
     * @param gFactory The graphics factory to create the sprite.
     * @param fileName The file name of the sprite.
     */
    protected void createSprite(GraphicsFactory gFactory, String fileName) {
        sprite = gFactory.getNewSprite(gFactory.getNewTexture(fileName));
    }

    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.DynamicBody;
    }

    private void takeDamageColor() {
        sprite.setColor(1, 0, 0, 1);
    }

    protected void removeColor() {
        sprite.setColor(Color.WHITE);
    }

    @Override
    public void setWieldedWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon cannot be null");
        } else {
            this.wieldedWeapon = weapon;
        }
    }

    public Weapon getWieldedWeapon() {
        return this.wieldedWeapon;
    }

    @Override
    public void setVelocity(Vector2 direction, int scale) {
        body.setLinearVelocity(direction.scl(scale));
    }

    protected abstract void updateAttackDelay();

    protected void updateTakeDamageDelay() {
        if (takeDamageDelay > 0) {
            takeDamageDelay--;
            if (takeDamageDelay == 0) {
                removeColor();
            }
        }
    }

    @Override
    public List<Item> getInventory() {
        return this.inventory;
    }

    @Override
    public int getAttackDelay() {
        return this.attackDelay;
    }

    protected abstract void updateSpritePosition();
    protected abstract void defineBody(Box2DFactory b2df, Vector2 position);
}
