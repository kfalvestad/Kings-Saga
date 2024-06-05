package com.kingssaga.game.model.attacks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;

/**
 * The Attack class represents an abstract attack in the game.
 * It contains information about the attack's strength, type, execution time, cooldown, range, and energy cost.
 */

public abstract class Attack implements Disposable {

    protected Sprite sprite;
    protected boolean isFlipped;
    protected Box2DFactory b2df;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected PolygonShape shape;
    protected Vector2 target;
    protected boolean isActive;
    protected int damageModifier;

    protected Weapon weapon;

    public Attack(Weapon weapon, Box2DFactory b2df, Sprite sprite) {
        this.weapon = weapon;
        this.b2df = b2df;
        this.sprite = sprite;
        isActive = false;
        isFlipped = false;
    }

    public abstract void performAttack(Vector2 position);
    public abstract String getSound();

    protected void defineBody() {
        bodyDef = b2df.getNewBodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        fixtureDef = b2df.getNewFixtureDef();
        fixtureDef.filter.categoryBits = Constants.ATTACK_BIT;
        fixtureDef.filter.maskBits = weapon.getMaskBits();

        shape = b2df.getNewPolygonShape();
        shape.setAsBox(weapon.getRange().x, weapon.getRange().y);
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
    }

    public void flipSprite() {
        sprite.flip(true, false);
        isFlipped = !isFlipped;
    }

    @Override
    public void dispose() {
        shape.dispose();
    }

    public int getDamage() {
        return weapon.getDamage() * damageModifier;
    }

    public abstract void draw(SpriteBatch batch);

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}
