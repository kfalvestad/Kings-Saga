package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.actors.enemies.Boss.BossState;
import com.kingssaga.game.model.attacks.Attack;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.items.weapons.MeleeWeapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class TestableBoss extends Boss {
    protected TestableBoss(FactoryManager fm, Vector2 position, SoundPlayer sp) {
        super(fm, position, sp);
    }

    @Override
    protected void setUp() {
        body = fm.getBox2DFactory().createBody(this, initialPosition);
        steering = fm.getBox2DFactory().getNewSteeringEntity(this.body, 5);
        steering.setMaxLinearSpeed(this.speed);
        steering.setMaxLinearAcceleration(this.acceleration);
    }

    public void updateOutsideRangeCounter(int i) {
        enemyOutsideRangeCounter = i;
    }

    public void updateInsideRangeCounter(int i) {
        enemyWithinRangeCounter = i;
    }
}

class BossTest {
    private TestableBoss boss;

    @BeforeEach
    void setUp() {
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        SoundPlayer sp = Mockito.mock(SoundPlayer.class);
        Box2DFactory b2df = new Box2DFactory();
        MeleeWeapon weapon = Mockito.mock(MeleeWeapon.class);
        when(weapon.getRange()).thenReturn(new Vector2(2, 2));
        when(weapon.getDelay()).thenReturn(50);
        Attack primaryAttack = Mockito.mock(Attack.class);
        when(weapon.getPrimaryAttack()).thenReturn(primaryAttack);
        Attack secondaryAttack = Mockito.mock(Attack.class);
        when(weapon.getSecondaryAttack()).thenReturn(secondaryAttack);
        when(fm.getBox2DFactory()).thenReturn(b2df);

        boss = new TestableBoss(fm, new Vector2(0, 0), sp);
        boss.setWieldedWeapon(weapon);
    }

    @Test
    void initialStateIsHunting() {
        assertEquals(BossState.HUNTING, boss.getCurrentState());
    }

    @Test
    void stateChangesToStompingWhenEnemyWithinRange() {
        Box2DLocation playerLocation = Mockito.mock(Box2DLocation.class);
        when(playerLocation.getPosition()).thenReturn(new Vector2(0, 0));
        boss.setPlayerLocation(playerLocation);
        boss.updateInsideRangeCounter(boss.ATTACK_WHEN_INSIDE_RANGE);
        boss.updateAttackingState();
        assertEquals(BossState.STOMPING, boss.getCurrentState());
    }

    @Test
    void stateChangesToChargingWhenEnemyOutsideRange() {
        Box2DLocation playerLocation = Mockito.mock(Box2DLocation.class);
        when(playerLocation.getPosition()).thenReturn(new Vector2(1000, 1000));
        boss.setPlayerLocation(playerLocation);
        boss.updateOutsideRangeCounter(boss.ATTACK_WHEN_OUTSIDE_RANGE);
        boss.updateAttackingState();
        assertEquals(BossState.CHARGING, boss.getCurrentState());
    }

    @Test
    void attackDelayDecreasesAfterAttack() {
        boss.setCurrentState(BossState.STOMPING);
        boss.doAttack();
        assertTrue(boss.getAttackDelay() > 0);
    }

    @Test
    void updateSteeringTest() {
        boss.updateSteering();
        assertNotNull(boss.getSteering());
    }

    @Test
    void doChargeAttackTest() {
        Box2DLocation location = Mockito.mock(Box2DLocation.class);
        when(location.getPosition()).thenReturn(new Vector2(1000, 1000));
        boss.setPlayerLocation(location);
        boss.setCurrentState(BossState.CHARGING);
        boss.doAttack();
        Mockito.verify(boss.getWieldedWeapon()).doPrimaryAttack(boss.getBody().getPosition());
    }

    @Test
    void updateAttackDelayTest() {
        boss.updateAttackDelay();
        assertEquals(0, boss.getAttackDelay());
    }
}