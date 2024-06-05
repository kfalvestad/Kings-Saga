package com.kingssaga.game.model;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.Box2DFactory;

/**
 * This class is strictly used because the enemy AI needs an instance of Steerable<Vector2> to work with collision avoidance.
 */
public class Obstacle implements Steerable<Vector2> {

    private final Body body;

    public Obstacle(Box2DFactory b2df, Rectangle rect) {
        BodyDef bodyDef = b2df.getNewBodyDef();
        FixtureDef fixtureDef = b2df.getNewFixtureDef();
        PolygonShape shape = b2df.getNewPolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        body = b2df.createBody(bodyDef);
        shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.OBJECT_BIT;
        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }
    @Override
    public Vector2 getLinearVelocity() {
        return new Vector2(0, 0);
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}
