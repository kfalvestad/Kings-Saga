package com.kingssaga.game.model.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;

import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.ItemType;
import com.kingssaga.game.model.items.potions.HealthPotion;

public class RandomLootFactoryTest {
    @Mock
    GameManager manager;
    @Mock
    ItemFactory itemFactory;
    @Mock
    CoinPouch coinPouch;
    @Mock
    HealthPotion healthPotion;

    List<ItemType> availableItems;

    private RandomLootFactory randomLootFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(manager.getItemFactory()).thenReturn(itemFactory);
        when(itemFactory.getNewItem(ItemType.COIN_POUCH)).thenReturn(coinPouch);
        when(itemFactory.getNewItem(ItemType.HEALTH_POTION)).thenReturn(healthPotion);

        randomLootFactory = new RandomLootFactory(manager);
        availableItems = new ArrayList<>();
    }

    @Test
    void createLootTestSingleItem() {
        availableItems.add(ItemType.COIN_POUCH);

        List<Item> loot = randomLootFactory.createLoot(availableItems, 1, 1);
        assertNotNull(loot);
        assertEquals(1, loot.size());
        assertTrue(loot.contains(coinPouch));
    }

    @Test
    void createLootTestMultipleItems() {
        availableItems.add(ItemType.COIN_POUCH);
        availableItems.add(ItemType.HEALTH_POTION);

        List<Item> loot = randomLootFactory.createLoot(availableItems, 2, 2);
        assertNotNull(loot);
        assertEquals(2, loot.size());
        assertTrue(loot.contains(coinPouch) || loot.contains(healthPotion));
    }

    @Test
    void createLootTestThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            randomLootFactory.createLoot(availableItems, -1, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            randomLootFactory.createLoot(availableItems, 1, -1);
        });
       assertThrows(IllegalArgumentException.class, () -> {
            randomLootFactory.createLoot(availableItems, 0, 0);
        });
        availableItems = null;
        assertThrows(IllegalArgumentException.class, () -> {
            randomLootFactory.createLoot(availableItems, 1, 1);
        });
        availableItems = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            randomLootFactory.createLoot(availableItems, 1, 1);
        });
    }


}
