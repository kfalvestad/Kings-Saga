package com.kingssaga.game.model.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.factories.FactoryManager;

import static org.junit.jupiter.api.Assertions.*;

class CoinPouchTest {
    private CoinPouch coinPouch;
    private FactoryManager factoryManager;

    @BeforeEach
    void setUp() {
        factoryManager = Mockito.mock(GameManager.class);
        Sprite sprite = Mockito.mock(Sprite.class);
        coinPouch = new CoinPouch(factoryManager, sprite);
    }

    @Test
    void getCoins_InitiallyZero_ReturnsZero() {
        assertEquals(0, coinPouch.getCoins());
    }

    @Test
    void addCoins_PositiveAmount_IncreasesCoins() {
        coinPouch.addCoins(10);
        assertEquals(10, coinPouch.getCoins());
    }

    @Test
    void addCoins_NegativeAmount_ThrowsError() {
        coinPouch.addCoins(10);
        assertThrows(IllegalArgumentException.class, () -> coinPouch.addCoins(-10));
        assertEquals(10, coinPouch.getCoins());
    }

    @Test
    void removeCoins_MoreThanAvailable_ReturnsFalse() {
        coinPouch.addCoins(10);
        assertFalse(coinPouch.removeCoins(20));
    }

    @Test
    void removeCoins_LessThanAvailable_ReturnsTrue() {
        coinPouch.addCoins(10);
        assertTrue(coinPouch.removeCoins(5));
        assertEquals(5, coinPouch.getCoins());
    }

    @Test
    void fillWithRandomAmount_NegativeMin_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> coinPouch.fillWithRandomAmount(-1, 10));
    }

    @Test
    void fillWithRandomAmount_NegativeMax_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> coinPouch.fillWithRandomAmount(10, -1));
    }

    @Test
    void fillWithRandomAmount_MinGreaterThanMax_FillsWithRandomAmount() {
        coinPouch.fillWithRandomAmount(10, 5);
        assertTrue(coinPouch.getValue() >= 5 && coinPouch.getValue() <= 10);
    }

    @Test
    void fillWithRandomAmount_MinEqualsMax_FillsWithExactAmount() {
        coinPouch.fillWithRandomAmount(10, 10);
        assertEquals(10, coinPouch.getValue());
    }

    @Test
    void multipleOperations_ReturnsExpectedResult() {
        // Add 10 coins
        coinPouch.addCoins(10);
        assertEquals(10, coinPouch.getCoins());

        // Remove 5 coins
        assertTrue(coinPouch.removeCoins(5));
        assertEquals(5, coinPouch.getCoins());

        // Try to remove more coins than available, should return false and not change
        // the coin count
        assertFalse(coinPouch.removeCoins(10));
        assertEquals(5, coinPouch.getCoins());

        // Fill with a random amount between 5 and 10
        coinPouch.fillWithRandomAmount(5, 10);
        assertTrue(coinPouch.getCoins() >= 5 && coinPouch.getCoins() <= 10);
    }
}