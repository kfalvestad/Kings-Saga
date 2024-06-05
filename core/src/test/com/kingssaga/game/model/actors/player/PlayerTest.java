package com.kingssaga.game.model.actors.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Consumable;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.Key;
import com.kingssaga.game.model.items.weapons.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    }
}

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        Box2DFactory b2df = new Box2DFactory();
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        when(fm.getBox2DFactory()).thenReturn(b2df);
        SoundPlayer sp = Mockito.mock(SoundPlayer.class);
        CoinPouch coinPouch = new CoinPouch(fm, Mockito.mock(Sprite.class));
        player = new TestablePlayer(fm, sp, coinPouch);
    }

    @Test
    void moveChangesVelocity() {
        assertTrue(player.getBody().getLinearVelocity().isZero());
        player.move(true, false, false, false);
        assertFalse(player.getBody().getLinearVelocity().isZero());
    }

    @Test
    void addToInventoryAddsItem() {
        Item item = Mockito.mock(Item.class);
        assertTrue(player.addToInventory(item));
        assertTrue(player.getInventory().contains(item));
    }

    @Test
    void addToInventoryDoesNotAddWhenFull() {
        for (int i = 0; i < Constants.INVENTORY_SIZE; i++) {
            player.addToInventory(Mockito.mock(Item.class));
        }
        Item extraItem = Mockito.mock(Item.class);
        assertFalse(player.addToInventory(extraItem));
        assertFalse(player.getInventory().contains(extraItem));
    }

    @Test
    void consumeItemAppliesEffect() {
        Consumable item = Mockito.mock(Consumable.class);
        when(item.getEffectType()).thenReturn(Consumable.EffectType.PERMANENT);
        player.consumeItem(item);
        Mockito.verify(item).consumeEffect(player);
    }

    @Test
    void consumeItemDoesNotApplyEffectWhenAffectedByPotion() {
        Consumable item = Mockito.mock(Consumable.class);
        when(item.getEffectType()).thenReturn(Consumable.EffectType.TEMPORARY);
        player.boostSpeed(1, 2);
        player.consumeItem(item);
        Mockito.verify(item, Mockito.never()).consumeEffect(player);
    }

    @Test
    void boostSpeedIncreasesSpeedTemporarily() {
        float initialSpeed = player.getSpeed();
        player.boostSpeed(1, 2);
        assertTrue(player.getSpeed() > initialSpeed);
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(initialSpeed, player.getSpeed());
    }

    @Test
    void hasKeyReturnsTrueWhenKeyIsInInventory() {
        Key key = Mockito.mock(Key.class);
        when(key.getID()).thenReturn(1);
        player.addToInventory(key);
        assertTrue(player.hasKey(1));
    }

    @Test
    void hasKeyReturnsFalseWhenKeyIsNotInInventory() {
        assertFalse(player.hasKey(1));
    }

    @Test
    void addCoinsIncreasesCoinCount() {
        int initialCoins = player.getCoins();
        player.addCoins(10);
        assertEquals(initialCoins + 10, player.getCoins());
    }

    @Test
    void attackTest() {
        Weapon weapon = Mockito.mock(Weapon.class);
        when(weapon.getDelay()).thenReturn(10);
        player.setWieldedWeapon(weapon);
        player.attack();
        assertTrue(player.getAttackDelay() > 0);
    }

    @Test
    void reduceAttackDelayTest() {
        Weapon weapon = Mockito.mock(Weapon.class);
        when(weapon.getDelay()).thenReturn(10);
        player.setWieldedWeapon(weapon);
        player.attack();
        int originalDelay = player.getAttackDelay();
        player.updateAttackDelay();
        assertTrue(originalDelay > player.getAttackDelay());
    }

    @Test
    void getLocationTest() {
        assertNotNull(player.getLocation());
    }

    @Test
    void isAffectedByPotionTest() {
        assertFalse(player.isAffectedByPotion());
        player.boostSpeed(1, 2);
        assertTrue(player.isAffectedByPotion());
    }

    @Test
    void removeCoinsTest() {
        player.addCoins(10);
        int initialCoins = player.getCoins();
        player.removeCoins(5);
        assertEquals(initialCoins - 5, player.getCoins());
    }

    @Test
    void giveRunningBootsTest() {
        player.giveRunningBoots();
        assertTrue(player.hasRunningBoots());
    }

    @Test
    void hasRunningBootsTest() {
        assertFalse(player.hasRunningBoots());
        player.giveRunningBoots();
        assertTrue(player.hasRunningBoots());
    }
}