package com.kingssaga.game.model.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.factories.FactoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyTest {
    private Key key;

    @BeforeEach
    void setUp() {
        FactoryManager fm = Mockito.mock(FactoryManager.class);
        int value = 10;
        Sprite sprite = new Sprite();
        int ID = 123;
        key = new Key(fm, value, sprite, ID);
    }

    @Test
    void testGetID() {
        int expectedID = 123;
        int actualID = key.getID();
        assertEquals(expectedID, actualID);
    }
}