package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EnemyTest {
    private Enemy enemy;
    private Item item1;
    private Item item2;
    private Location<Vector2> playerLocation;

    @BeforeEach
    void setUp() {
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        SoundPlayer sp = Mockito.mock(SoundPlayer.class);
        Box2DFactory b2df = new Box2DFactory();
        when(fm.getBox2DFactory()).thenReturn(b2df);
        enemy = new TestableSimpleEnemy(fm, new Vector2(0, 0), sp);
        item1 = Mockito.mock(Item.class);
        item2 = Mockito.mock(Item.class);
        playerLocation = Mockito.mock(Location.class);
    }

    @Test
    void setPlayerLocation_updatesPlayerLocation() {
        enemy.setPlayerLocation(playerLocation);
        assertEquals(playerLocation, enemy.playerLocation);
    }

    @Test
    void getSteering_returnsSteering() {
        Steerable<Vector2> steering = enemy.getSteering();
        assertEquals(enemy.steering, steering);
    }

    @Test
    void getLoot_returnsLoot() {
        enemy.addToLoot(item1);
        enemy.addToLoot(item2);
        List<Item> loot = enemy.getLoot();
        assertTrue(loot.contains(item1));
        assertTrue(loot.contains(item2));
    }

    @Test
    void addToLoot_singleItem_addsItemToLoot() {
        enemy.addToLoot(item1);
        assertTrue(enemy.getLoot().contains(item1));
    }

    @Test
    void addToLoot_listOfItems_addsItemsToLoot() {
        List<Item> items = Arrays.asList(item1, item2);
        enemy.addToLoot(items);
        assertTrue(enemy.getLoot().containsAll(items));
    }

    @Test
    void dropLoot_dropsAllItems() {
        enemy.addToLoot(item1);
        enemy.addToLoot(item2);
        enemy.dropLoot();
        assertTrue(enemy.getLoot().isEmpty());
    }

    @Test
    void getSteeringTest() {
        assertNotNull(enemy.getSteering());
    }

    @Test
    void getLootTest() {
        assertNotNull(enemy.getLoot());
    }

    @Test
    void addToLootTest() {
        enemy.addToLoot(item1);
        assertTrue(enemy.getLoot().contains(item1));
    }

    @Test
    void addToLootTest2() {
        List<Item> loot = new ArrayList<>();
        loot.add(item1);
        loot.add(item2);
        enemy.addToLoot(loot);
        assertTrue(enemy.getLoot().contains(item1));
        assertTrue(enemy.getLoot().contains(item2));
    }

    @Test
    void dropLootTest() {
        enemy.addToLoot(item1);
        enemy.addToLoot(item2);
        enemy.dropLoot();
        assertTrue(enemy.getLoot().isEmpty());
    }

    @Test
    void dieTest() {
        List<Item> itemOnGround = new ArrayList<>();
        enemy.addToLoot(item1);
        enemy.addToLoot(item2);
        enemy.die(itemOnGround);
        assertTrue(itemOnGround.contains(item1));
        assertTrue(itemOnGround.contains(item2));
        assertTrue(enemy.getLoot().isEmpty());
    }
}
