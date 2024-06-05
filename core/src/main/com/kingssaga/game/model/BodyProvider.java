package com.kingssaga.game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * The BodyProvider interface represents an object that provides a body type.
 */
public interface BodyProvider {
    
    /**
     * Gets the body type.
     * 
     * @return the body type
     */
    BodyDef.BodyType getBodyType();
}
