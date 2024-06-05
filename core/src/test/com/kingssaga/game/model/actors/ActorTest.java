package com.kingssaga.game.model.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.actions.TakeDamageAction;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.weapons.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TestablePlayer extends Player {
    protected TestablePlayer(FactoryManager fm, SoundPlayer sp, CoinPouch coinPouch) {
        super(fm, sp, coinPouch);
    }

    @Override
    protected void setUp() {
        body = fm.getBox2DFactory().createBody(this, Constants.PLAYER_SPAWN);
        location = fm.getBox2DFactory().getNewBox2DLocation(body);
        sprite = new Sprite();
    }
}

class ActorTest {
    private Actor actor;
    private Weapon mockWeapon;
    private TakeDamageAction mockAction;

    @BeforeEach
    void setUp() {
        Box2DFactory b2df = new Box2DFactory();
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        when(fm.getBox2DFactory()).thenReturn(b2df);
        SoundPlayer sp = Mockito.mock(SoundPlayer.class);
        actor = new TestablePlayer(fm, sp, Mockito.mock(CoinPouch.class));
        mockWeapon = Mockito.mock(Weapon.class);
        mockAction = Mockito.mock(TakeDamageAction.class);
    }

    @Test
    void setHealth_withinValidRange_setsHealth() {
        int health = new Random().nextInt(Constants.PLAYER_MAX_HEALTH);
        actor.setHealth(health);
        assertEquals(health, actor.getHealth());
    }

    @Test
    void setHealth_aboveMaxHealth_setsHealthToMax() {
        actor.setHealth(200);
        assertEquals(actor.maxHealth, actor.getHealth());
    }

    @Test
    void setHealth_belowZero_doesNotChangeHealth() {
        int initialHealth = actor.getHealth();
        actor.setHealth(-10);
        assertEquals(initialHealth, actor.getHealth());
    }

    @Test
    void restoreHealth_validAmount_increasesHealth() {
        actor.setHealth(5);
        actor.restoreHealth(10);
        assertEquals(15, actor.getHealth());
    }

    @Test
    void restoreHealth_exceedsMaxHealth_setsHealthToMax() {
        actor.setHealth(90);
        actor.restoreHealth(20);
        assertEquals(actor.maxHealth, actor.getHealth());
    }

    @Test
    void isWithinRange_withinRange_returnsTrue() {
        actor.setPosition(new Vector2(5, 5));
        Vector2 targetPosition = new Vector2(6, 6);
        Vector2 attackRange = new Vector2(2, 2);
        assertTrue(actor.isWithinRange(targetPosition, attackRange));
    }

    @Test
    void isWithinRange_outsideRange_returnsFalse() {
        actor.setPosition(new Vector2(5, 5));
        Vector2 targetPosition = new Vector2(8, 8);
        Vector2 attackRange = new Vector2(2, 2);
        assertFalse(actor.isWithinRange(targetPosition, attackRange));
    }

    @Test
    void takeDamage_executesAttackAction() {
        actor.takeDamage(mockAction);
        Mockito.verify(mockAction).execute();
    }

    @Test
    void setWieldedWeapon_validWeapon_setsWeapon() {
        actor.setWieldedWeapon(mockWeapon);
        assertEquals(mockWeapon, actor.getWieldedWeapon());
    }

    @Test
    void setWieldedWeapon_nullWeapon_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> actor.setWieldedWeapon(null));
    }

    @Test
    void isAliveTest() {
        actor.setHealth(10);
        assertTrue(actor.isAlive());
        actor.setHealth(0);
        assertFalse(actor.isAlive());
    }

    @Test
    void getSpeedTest() {
        actor.setSpeed(10);
        assertEquals(10, actor.getSpeed());
    }

    @Test
    void setSpeedTest() {
        actor.setSpeed(10);
        assertEquals(10, actor.getSpeed());
        actor.setSpeed(-10);
        assertEquals(10, actor.getSpeed());
        actor.setSpeed(1000);
        assertEquals(10, actor.getSpeed());
    }

    @Test
    void getPositionTest() {
        actor.setPosition(new Vector2(10, 10));
        assertEquals(new Vector2(10, 10), actor.getPosition());
    }

    @Test
    void setPositionTest() {
        actor.setPosition(new Vector2(10, 10));
        assertEquals(new Vector2(10, 10), actor.getPosition());
        assertThrows(IllegalArgumentException.class, () -> actor.setPosition(new Vector2(-10, 10)));
        assertThrows(IllegalArgumentException.class, () -> actor.setPosition(new Vector2(10, -10)));
    }

    @Test
    void getBodyTest() {
        assertNotNull(actor.getBody());
    }


    @Test
    void setWieldedWeaponTest() {
        Weapon weapon = Mockito.mock(Weapon.class);
        actor.setWieldedWeapon(weapon);
        assertEquals(weapon, actor.getWieldedWeapon());
    }

    @Test
    void setWieldedWeaponNullTest() {
        assertThrows(IllegalArgumentException.class, () -> actor.setWieldedWeapon(null));
    }

    @Test
    void getWieldedWeaponTest() {
        Weapon weapon = Mockito.mock(Weapon.class);
        actor.setWieldedWeapon(weapon);
        assertEquals(weapon, actor.getWieldedWeapon());
    }

}