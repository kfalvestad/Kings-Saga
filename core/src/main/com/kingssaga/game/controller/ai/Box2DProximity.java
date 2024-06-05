package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.model.actors.enemies.Enemy;

/** From: <a href="https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dSquareAABBProximity.java">...</a> */
public class Box2DProximity implements Proximity<Vector2>, QueryCallback {

    private Steerable<Vector2> owner;
    private final World world;
    private final float detectionRadius;
    private int neighborCount;
    private ProximityCallback<Vector2> callback;

    public Box2DProximity(Steerable<Vector2> owner, World world, float detectionRadius) {
        this.owner = owner;
        this.world = world;
        this.detectionRadius = detectionRadius;
    }

    @Override
    public Steerable<Vector2> getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Steerable<Vector2> owner) {
        this.owner = owner;
    }

    @Override
    public int findNeighbors(ProximityCallback<Vector2> callback) {
        this.callback = callback;
        neighborCount = 0;
        world.QueryAABB(this, owner.getPosition().x - detectionRadius, owner.getPosition().y - detectionRadius, owner.getPosition().x + detectionRadius, owner.getPosition().y + detectionRadius);
        this.callback = null;
        return neighborCount;
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        Object obj = fixture.getUserData();
        if (obj instanceof Enemy) {
            if (callback.reportNeighbor(((Enemy) obj).getSteering())) neighborCount++;
        }
        return true;
    }
}

