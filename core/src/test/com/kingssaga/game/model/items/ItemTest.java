package com.kingssaga.game.model.items;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.TestActor;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.badlogic.gdx.graphics.Texture;

class ItemTest {
    private FactoryManager fm;
    private Sprite sprite;
    private Item item;

    @BeforeEach
    void setUp() {
        fm = Mockito.mock(GameManager.class);
        sprite = Mockito.mock(Sprite.class);
        when(sprite.getTexture()).thenReturn(Mockito.mock(Texture.class));
    }

    @Test
    void testConstructor() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        assertEquals(item.getName(), "Test Item");
        assertEquals(item.getValue(), 10);
    }

    @Test
    void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            item = new TestableItem(fm, null, 10, sprite);
        });
    }

    @Test
    void testConstructorNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            item = new TestableItem(fm, "Test Item", -10, sprite);
        });
    }

    @Test
    void testGetOwner() {
        item = new TestableItem(fm, "Test Item", 0, sprite);
        assertNull(item.getOwner());

        Item item2 = new TestableItem(fm, "Test Item 2", 0, sprite);
        Actor owner = new TestActor();
        item2.setOwner(owner);
        assertEquals(item2.getOwner(), owner);
    }

    @Test
    void testSetOwner() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        Actor owner = new TestActor();
        item.setOwner(owner);
        assertEquals(item.getOwner(), owner);
    }

    @Test
    void testGetValue() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        assertEquals(item.getValue(), 10);
    }

    @Test
    void testSetValue() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        item.setValue(20);
        assertEquals(item.getValue(), 20);
    }

    @Test
    void testSetValueNegative() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        assertThrows(IllegalArgumentException.class, () -> {
            item.setValue(-10);
        });
    }

    @Test
    void testDrop() {
        Box2DFactory b2df = new Box2DFactory();
        when(fm.getBox2DFactory()).thenReturn(b2df);
        item = new TestableItem(fm, "Test Item", 10, sprite);
        Player player = new TestablePlayer2(fm, Mockito.mock(SoundPlayer.class), Mockito.mock(CoinPouch.class));
        player.addToInventory(item);
        assertTrue(player.getInventory().contains(item));
        item.drop(player.getPosition());
        // Check that the item is in the world at the player's position
        assertEquals(b2df.getNewBox2DLocation(item.getBody()).getPosition(), player.getPosition());
        // TODO: Currently the item is not removed from the player's inventory
    }

    @Test
    void testDropNullPosition() {
        Box2DFactory b2df = new Box2DFactory();
        when(fm.getBox2DFactory()).thenReturn(b2df);
        item = new TestableItem(fm, "Test Item", 10, sprite);
        assertThrows(NullPointerException.class, () -> {
            item.drop(null);
        });
    }

    @Test
    void handleInteractionTest() {
        item = new TestableItem(fm, "Test Item", 10, sprite);
        Player player = Mockito.mock(Player.class);
        assertTrue(item.handleInteraction(player));
        verify(player).addToInventory(item);
    }

}



// TODO: Move these classes to their own files and avoid duplication with PlayerTest
class TestablePlayer2 extends Player {
    public TestablePlayer2(FactoryManager fm, SoundPlayer sp, CoinPouch coinPouch) {
        super(fm, sp, coinPouch);
    }

    @Override
    protected void setUp() {
        body = fm.getBox2DFactory().createBody(this, Constants.PLAYER_SPAWN);
        location = fm.getBox2DFactory().getNewBox2DLocation(body);
    }
}

class TestableCoinPouch extends CoinPouch {
    public TestableCoinPouch(FactoryManager fm) {
        super(fm, Mockito.mock(Sprite.class));
    }

    // @Override
    // protected void setUp() {
    // }

}