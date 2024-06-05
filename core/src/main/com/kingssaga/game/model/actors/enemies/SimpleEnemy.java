package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.ItemType;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SimpleEnemy extends Enemy  {
    private final float FLIP_SPRITE_MARGIN = 0.6f;

    protected enum State {
        ATTACKING, LOW_HEALTH
    }
    private State currentState;
    private final List<ItemType> availableLootItems = new ArrayList<>();

    private Vector2 vision;

    protected Fixture fixture;

    public SimpleEnemy(FactoryManager fm, Vector2 position, SoundPlayer sp) {
        super(Constants.SIMPLE_ENEMY_HEALTH, Constants.SIMPLE_ENEMY_SPEED, Constants.SIMPLE_ENEMY_ACCELERATION, sp);
        this.fm = fm;
        initialPosition = position;
        currentState = State.ATTACKING;
        takeDamageDelay = 0;
        isFacingLeft = false;
        attackDelay = 0;
        maxHealth = Constants.SIMPLE_ENEMY_MAX_HEALTH;
        vision = new Vector2(5, 5);
        setUp();
    }

    protected void setUp() {
        createSprite(fm.getGraphicsFactory(), Constants.SIMPLE_ENEMY_SPRITE_PATH);
        defineBody(fm.getBox2DFactory(), initialPosition);
        steering = fm.getBox2DFactory().getNewSteeringEntity(this.body, width);

        steering.setMaxLinearSpeed(this.speed);
        steering.setMaxLinearAcceleration(this.acceleration);
        steering.setMaxLinearAcceleration(1);
        this.coinPouch = (CoinPouch) fm.getItemFactory().getNewItem(ItemType.COIN_POUCH);
        this.availableLootItems.add(ItemType.MELEE_WEAPON);
        this.availableLootItems.add(ItemType.HEALTH_POTION);
        this.availableLootItems.add(ItemType.SPEED_POTION);
        addToLoot(fm.getLootFactory().createLoot(availableLootItems, 0, 2));
        addToLoot(coinPouch);
    }

    private void updateFilterBits() {
        Filter filter = new Filter();
        filter.categoryBits = Constants.ENEMY_HITBOX_BIT;
        filter.maskBits = Constants.ATTACK_BIT;
        fixture.setFilterData(filter);
    }

    private void flee() {
        behavior = getFleeBehavior();
        updateFilterBits();
        vision = new Vector2(3, 3);
        sp.playSound("scream.mp3");
    }

    private BlendedSteering<Vector2> getFleeBehavior() {
        return new BlendedSteering<>(steering).add(new Flee<>(steering, playerLocation), 1);
    }

    protected void updateBehavior() {
        if (health <= 10 && currentState == State.ATTACKING) {
            int random = new Random().nextInt(5);
            if (random == 1) {
                flee();
            }
            currentState = State.LOW_HEALTH;
        }
    }

    @Override
    protected void updateAttackDelay() {
        if (attackDelay == 0 && isWithinRange(playerLocation.getPosition(), wieldedWeapon.getRange())) {
            wieldedWeapon.doPrimaryAttack(body.getPosition());
            attackDelay = wieldedWeapon.getDelay();
        } else if (attackDelay > 0) {
            attackDelay--;
        }
    }

    @Override
    protected void updateSteering() {
        if (isWithinRange(playerLocation.getPosition(), vision)) {
            steering.update(behavior);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }

    @Override
    protected void updateSpritePosition() {
        sprite.setPosition(
                body.getPosition().x - width / 2,
                body.getPosition().y - height / 2
        );
    }

    public void update() {
        updateBehavior();
        updateAttackDelay();
        updateSteering();
        updateFacing(FLIP_SPRITE_MARGIN);
        updateSpritePosition();
        updateTakeDamageDelay();
    }

    @Override
    protected void defineBody(Box2DFactory b2df, Vector2 position) {
        width = sprite.getWidth() / Constants.PPM;
        height = sprite.getHeight() / Constants.PPM;
        sprite.setBounds(0, 0, width, height);

        body = b2df.createBody(this, position);

        FixtureDef fixtureDef = b2df.getNewFixtureDef();
        fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
        fixtureDef.filter.maskBits = Constants.OBJECT_BIT | Constants.EXIT_BIT | Constants.DOOR_BIT;

        PolygonShape shape = b2df.getNewPolygonShape();
        shape.setAsBox(width / 4, 1 / Constants.PPM, new Vector2(0, -height / 2), 0);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        shape.setAsBox(width / 2, height / 2);
        fixtureDef.filter.categoryBits = Constants.ENEMY_HITBOX_BIT;
        fixtureDef.filter.maskBits = Constants.ATTACK_BIT | Constants.ENEMY_HITBOX_BIT;
        fixture = body.createFixture((fixtureDef));
        fixture.setUserData(this);

        shape.dispose();
    }

    protected State getCurrentState() {
        return currentState;
    }
}
