package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;

/**
 * Represents a Box2D entity that can be steered using steering behaviors.
 */
public class Box2DSteeringEntity implements Steerable<Vector2> {

    private final Body body;

    private final SteeringAcceleration<Vector2> steeringOutput;
    private final float boundingRadius;
    private boolean tagged;

    private float maxLinearSpeed;
    private float maxLinearAcceleration;

    /**
     * Constructs a new Box2DSteeringEntity.
     *
     * @param body           the Box2D body associated with this entity
     * @param boundingRadius the bounding radius of this entity
     */
    public Box2DSteeringEntity(Body body, float boundingRadius) {
        this.body = body;
        this.boundingRadius = boundingRadius;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());

        tagged = false;
    }

    /**
     * Updates the entity's position and orientation based on the current steering behavior.
     *
     * @param steeringBehavior the steering behavior to apply
     */
    public void update(SteeringBehavior<Vector2> steeringBehavior) {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringOutput);

            if (!steeringOutput.linear.isZero()) {
                body.setLinearVelocity(steeringOutput.linear);
            }
        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /*
     * The following methods are not used in this class, however, they are
     * required to be implemented
     */

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
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 1f;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        throw new UnsupportedOperationException();
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
