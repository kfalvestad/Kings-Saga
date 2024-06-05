package com.kingssaga.game.model;

import com.badlogic.gdx.physics.box2d.*;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.actions.TakeDamageAction;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.enemies.Boss;
import com.kingssaga.game.model.attacks.Attack;

public class WorldContactListener implements ContactListener {
    private GameManager manager;

    public void setManager(GameManager manager) {
        this.manager = manager;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object objectA = fixtureA.getUserData();
        Object objectB = fixtureB.getUserData();

        int bitsA = fixtureA.getFilterData().categoryBits;
        int bitsB = fixtureB.getFilterData().categoryBits;

        if (bitsA == Constants.PLAYER_BIT) manager.handlePlayerContact(objectB);
        if (bitsB == Constants.PLAYER_BIT) manager.handlePlayerContact(objectA);
        if (bitsB == Constants.PLAYER_HITBOX_BIT) manager.handlePlayerContact(objectA);
        if (bitsA == Constants.PLAYER_HITBOX_BIT) manager.handlePlayerContact(objectB);

        if (bitsA == Constants.ATTACK_BIT) {
            Actor target = (Actor) objectB;
            Attack attack = (Attack) objectA;
            int damage = attack.getDamage();
            target.takeDamage(new TakeDamageAction(damage, target));
        }

        if (bitsB == Constants.ATTACK_BIT) {
            Actor target = (Actor) objectA;
            Attack attack = (Attack) objectB;
            int damage = attack.getDamage();
            target.takeDamage(new TakeDamageAction(damage, target));
        }

        if (bitsA == Constants.ENEMY_BIT) {
            if (objectA instanceof Boss)
                if (((Boss) objectA).getCurrentState() == Boss.BossState.CHARGING) {
                    ((Boss) objectA).setCurrentState(Boss.BossState.HUNTING);
                }
        }

        if (bitsB == Constants.ENEMY_BIT) {
            if (objectB instanceof Boss)
                if (((Boss) objectB).getCurrentState() == Boss.BossState.CHARGING) {
                    ((Boss) objectB).setCurrentState(Boss.BossState.HUNTING);
                }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int bitsA = fixtureA.getFilterData().categoryBits;
        int bitsB = fixtureB.getFilterData().categoryBits;

        if (bitsA == Constants.PLAYER_HITBOX_BIT && bitsB == Constants.NPC_BIT) manager.handleEndNPCContact();
        if (bitsB == Constants.PLAYER_HITBOX_BIT && bitsA == Constants.NPC_BIT) manager.handleEndNPCContact();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
       
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
      
    }
}
