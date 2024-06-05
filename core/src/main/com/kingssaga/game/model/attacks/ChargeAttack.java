package com.kingssaga.game.model.attacks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;

public class ChargeAttack extends Attack {

    private final String sound;
    int swingTimer;
    private Box2DLocation targetLocation;

    public ChargeAttack(Weapon weapon, Box2DFactory b2df, Sprite sprite, Box2DLocation targetLocation) {
        super(weapon, b2df, sprite);
        this.sound = "axe.mp3";
        this.targetLocation = targetLocation;
        damageModifier = 1;
        defineBody();}

    @Override
    public void performAttack(Vector2 position) {
        Vector2 target = targetLocation.getPosition();
        isActive = true;
        swingTimer = 100;
        Vector2 direction = new Vector2(target.x - position.x, target.y - position.y);
        direction.nor();
        weapon.getOwner().setVelocity(direction, 10);
    }

    private void swingWeapon() {
        Vector2 position = weapon.getOwner().getPosition();
        float spritePositionX = getSpriteX(position.x);
        Vector2 range = weapon.getRange();

        bodyDef.position.set(position.x, position.y);
        Body body = b2df.createBody(bodyDef);

        PolygonShape shape = b2df.getNewPolygonShape();
        shape.setAsBox(range.x / 2, range.y / 2);
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        sprite.setBounds(0, 0, range.x, range.y);
        sprite.setPosition(spritePositionX, body.getPosition().y - sprite.getHeight() / 2);
        b2df.removeBodyAfterDelay(body, 1);

        shape.dispose();
    }

    @Override
    public String getSound() {
        return this.sound;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (swingTimer % 20 == 0) {
            swingWeapon();
            sprite.draw(batch);
            weapon.playSound(sound);
        }
        swingTimer--;

        if (swingTimer == 0) {
            isActive = false;
        }


    }

    private float getSpriteX(float posX) {
        return isFlipped ? posX - weapon.getOwner().getWidth() - 0.1f: posX + weapon.getOwner().getWidth() / 3;
    }
}
