package com.kingssaga.game.model.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;

public class SlamAttack extends Attack {
    private final String sound;
    private Animation<TextureRegion> animation;
    private float stateTime;


    public SlamAttack(Weapon weapon, Box2DFactory b2df, Sprite sprite) {
        super(weapon, b2df, sprite);
        damageModifier = 2;
        sound = "slam.mp3";
        isActive = false;
        defineBody();
        defineAnimation();
        sprite.setRegion(animation.getKeyFrame(stateTime));
    }

    @Override
    public void performAttack(Vector2 position) {
        isActive = true;
        Vector2 range = weapon.getRange();

        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position.x, position.y);
        Body body = b2df.createBody(bodyDef);

        FixtureDef fixtureDef = b2df.getNewFixtureDef();

        fixtureDef.filter.categoryBits = Constants.ATTACK_BIT;
        fixtureDef.filter.maskBits = weapon.getMaskBits();

        PolygonShape shape = b2df.getNewPolygonShape();
        shape.setAsBox(range.x, range.y);
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        sprite.setBounds(0, 0, range.x*2.5f, range.y*2.5f);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        b2df.removeBodyAfterDelay(body, 2);
        shape.dispose();
    }


    @Override
    public String getSound() {
        return this.sound;
    }

    private void defineAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(sprite.getTexture(), i * 256, 0, 256, 128));
        }
        animation = new Animation<>(0.1f, frames);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isActive && !animation.isAnimationFinished(stateTime)) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
            sprite.setRegion(currentFrame);
            sprite.draw(batch);
        } else {
            isActive = false;
            stateTime = 0;
        }
        }
}
