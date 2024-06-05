package com.kingssaga.game.model.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.ItemType;
import com.kingssaga.game.model.items.Key;
import com.kingssaga.game.model.items.potions.HealthPotion;
import com.kingssaga.game.model.items.potions.SpeedPotion;
import com.kingssaga.game.model.items.weapons.MeleeWeapon;
import com.kingssaga.game.model.items.weapons.Weapon;

public class ItemFactoryTest {

    @Mock
    private GameManager manager;
    @Mock
    private Texture texture;
    @Mock
    private Sprite sprite;
    @Mock
    GraphicsFactory graphicsFactory;
    @Mock
    WeaponFactory weaponFactory;
    @Mock
    SoundPlayer sp;

    private ItemFactory itemFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(manager.getGraphicsFactory()).thenReturn(graphicsFactory);
        when(graphicsFactory.getNewSprite(any(Texture.class))).thenReturn(sprite);
        when(graphicsFactory.getNewTexture(anyString())).thenReturn(texture);
        when(manager.getWeaponFactory()).thenReturn(weaponFactory);
        when(weaponFactory.createWeapon(anyString(), any(), anyInt(), anyInt())).thenReturn(new MeleeWeapon(manager, "sword", new Vector2(1f, 1f), 1, 1, sprite, sp));
        
        itemFactory = new ItemFactory(manager);
    }

    @Test
    public void testGetNewItem_HealthPotion() {
        Item item = itemFactory.getNewItem(ItemType.HEALTH_POTION);
        assertNotNull(item);
        assertTrue(item instanceof HealthPotion);
    }

    @Test
    public void testGetNewItem_SpeedPotion() {
        Item item = itemFactory.getNewItem(ItemType.SPEED_POTION);
        assertNotNull(item);
        assertTrue(item instanceof SpeedPotion);
    }

    @Test
    public void testGetNewItem_CoinPouch() {
        Item item = itemFactory.getNewItem(ItemType.COIN_POUCH);
        assertNotNull(item);
        assertTrue(item instanceof CoinPouch);
    }

    @Test
    public void testGetNewItem_InvalidItemType() {
        assertThrows(Exception.class, () -> {
            itemFactory.getNewItem(null);
        });
    }

    @Test
    public void testGetNewWeapon() {
        Weapon weapon = (Weapon)itemFactory.getNewItem(ItemType.MELEE_WEAPON);
        assertNotNull(weapon);
    }

    @Test
    public void testGetNewKey() {
        Key key = itemFactory.getNewKey(1);
        assertNotNull(key);
        assertEquals(1, key.getID());
        assertTrue(key instanceof Key);
    }
}