package com.kingssaga.game.model.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kingssaga.game.controller.ai.Box2DSteeringEntity;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.WorldContactListener;
import com.kingssaga.game.model.actors.IActor;

import java.util.HashMap;
import java.util.Map;

/**
 * The Box2DFactory class is responsible for creating various Box2D objects
 * used in the game.
 */
public class Box2DFactory implements Disposable {

    private final World world;
    private final WorldContactListener contactListener;
    private final Map<Body, Integer> bodiesToBeDestroyed;

    public Box2DFactory() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        contactListener = new WorldContactListener();
        world.setContactListener(contactListener);
        bodiesToBeDestroyed = new HashMap<>();
    }

    /**
     * Creates a new BodyDef object.
     *
     * @return a new BodyDef object
     */
    public BodyDef getNewBodyDef() {
        return new BodyDef();
    }


    /**
     * Creates a new FixtureDef object.
     *
     * @return a new FixtureDef object
     */
    public FixtureDef getNewFixtureDef() {
        return new FixtureDef();
    }

    /**
     * Creates a new PolygonShape object.
     *
     * @return a new PolygonShape object
     */
    public PolygonShape getNewPolygonShape() {
        return new PolygonShape();
    }

    /**
     * Creates a new Box2DLocation object with the specified Body.
     *
     * @param body the Box2D body to associate with the location
     * @return a new Box2DLocation object
     */
    public Box2DLocation getNewBox2DLocation(Body body) {
        return new Box2DLocation(body);
    }

    /**
     * Creates a new Box2DSteeringEntity object with the specified Body and bounding radius.
     *
     * @param body           the Box2D body to associate with the steering entity
     * @param boundingRadius the bounding radius of the steering entity
     * @return a new Box2DSteeringEntity object
     */
    public Box2DSteeringEntity getNewSteeringEntity(Body body, float boundingRadius) {
        return new Box2DSteeringEntity(body, boundingRadius);
    }

    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public Body createBody(IActor actor, Vector2 position) {
        BodyDef bodyDef = getNewBodyDef();
        bodyDef.type = actor.getBodyType();
        bodyDef.position.set(position);
        return world.createBody(bodyDef);
    }

    /**
     * Adds bodies to the list of bodies to be destroyed after a given delay.
     */
    public void removeBodyAfterDelay(Body body, int delay) {
        bodiesToBeDestroyed.put(body, delay);
    }


    public WorldContactListener getContactListener() {
        return contactListener;
    }

    public Map<Body, Integer> getBodiesToBeDestroyed() {
        return bodiesToBeDestroyed;
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }

    public void getBodies(Array<Body> bodies) {
        world.getBodies(bodies);
    }

    public void step() {
        world.step(1 / 60f, 6, 2);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
    }

}