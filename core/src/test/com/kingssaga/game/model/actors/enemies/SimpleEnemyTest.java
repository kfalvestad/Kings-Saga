package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.items.weapons.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TestableSimpleEnemy extends SimpleEnemy {
    protected TestableSimpleEnemy(FactoryManager fm, Vector2 position, SoundPlayer sp) {
        super(fm, position, sp);
    }

    @Override
    protected void setUp() {
        body = fm.getBox2DFactory().createBody(this, initialPosition);
        steering = fm.getBox2DFactory().getNewSteeringEntity(this.body, 5);
        steering.setMaxLinearSpeed(this.speed);
        steering.setMaxLinearAcceleration(this.acceleration);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new PolygonShape();
        fixture = body.createFixture(fixtureDef);
    }
}

class SimpleEnemyTest {
    private SimpleEnemy simpleEnemy;

    @BeforeEach
    void setUp() {
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        SoundPlayer sp = Mockito.mock(SoundPlayer.class);
        Box2DFactory b2df = new Box2DFactory();
        when(fm.getBox2DFactory()).thenReturn(b2df);
        simpleEnemy = new TestableSimpleEnemy(fm, new Vector2(0, 0), sp);
    }

    @Test
    void enemyInitializesWithCorrectState() {
        assertEquals(SimpleEnemy.State.ATTACKING, simpleEnemy.getCurrentState());
    }

    @Test
    void enemySwitchesToLowHealthStateWhenHealthIsOne() {
        simpleEnemy.setHealth(1);
        simpleEnemy.updateBehavior(); 
        assertEquals(SimpleEnemy.State.LOW_HEALTH, simpleEnemy.getCurrentState());
    }

    @Test
    void enemyDoesNotSwitchToLowHealthStateWhenHealthIsAboveTen() {
        simpleEnemy.setHealth(11);
        simpleEnemy.updateBehavior();
        assertEquals(SimpleEnemy.State.ATTACKING, simpleEnemy.getCurrentState());
    }

    @Test
    void enemyDoesNotMoveWhenPlayerIsOutOfVisionRange() {
        Box2DLocation playerLocation = Mockito.mock(Box2DLocation.class);
        when(playerLocation.getPosition()).thenReturn(new Vector2(1000, 1000));
        simpleEnemy.setPlayerLocation(playerLocation);
        simpleEnemy.updateSteering();
        assertTrue(simpleEnemy.getBody().getLinearVelocity().isZero());
    }

    @Test
    void enemyMovesWhenPlayerIsWithinVisionRange() {
        Box2DLocation playerLocation = Mockito.mock(Box2DLocation.class);
        when(playerLocation.getPosition()).thenReturn(new Vector2(1, 1));
        simpleEnemy.setPlayerLocation(playerLocation);
        simpleEnemy.setBehavior();
        simpleEnemy.updateSteering();
        assertFalse(simpleEnemy.getBody().getLinearVelocity().isZero());
    }

     @Test
    void updateAttackDelayTest() {
        Weapon weapon = Mockito.mock(Weapon.class);
        when(weapon.getDelay()).thenReturn(50);
        when(weapon.getRange()).thenReturn(new Vector2(100, 100));
        Box2DLocation location = Mockito.mock(Box2DLocation.class);
        when(location.getPosition()).thenReturn(new Vector2(1, 1));
        simpleEnemy.setPlayerLocation(location);
        simpleEnemy.setWieldedWeapon(weapon);
        simpleEnemy.updateAttackDelay();
        assertEquals(50, simpleEnemy.getAttackDelay());
     }
}