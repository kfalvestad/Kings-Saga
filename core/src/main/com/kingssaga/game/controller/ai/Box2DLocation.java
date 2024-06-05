package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Represents a location in a Box2D physics world.
 * Implements the Location interface from the libGDX AI library.
 */
/**
 * Represents a location in a Box2D physics world.
 */
public class Box2DLocation implements Location<Vector2> {

    private final Body body;

    /**
     * Constructs a Box2DLocation object with the given Body.
     * 
     * @param body The Box2D body associated with this location.
     */
    public Box2DLocation (Body body) {
        this.body = body;
    }

    /**
     * Returns the position of the Box2D body.
     * 
     * @return The position vector of the body.
     */
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Returns the orientation of the Box2D body.
     * 
     * @return The orientation angle of the body.
     */
    @Override
    public float getOrientation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported.
     * Sets the orientation of the Box2D body.
     * 
     * @param orientation The new orientation angle for the body.
     */
    @Override
    public void setOrientation(float orientation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported.
     * Converts a vector to an angle.
     * 
     * @param vector The vector to convert.
     * @return The angle represented by the vector.
     */
    @Override
    public float vectorToAngle(Vector2 vector) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported.
     * Converts an angle to a vector.
     * 
     * @param outVector The output vector to store the result.
     * @param angle The angle to convert.
     * @return The vector represented by the angle.
     */
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported.
     * Creates a new instance of Box2DLocation.
     * 
     * @return A new instance of Box2DLocation.
     */
    @Override
    public Location<Vector2> newLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
