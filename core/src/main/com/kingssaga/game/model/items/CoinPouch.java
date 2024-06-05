package com.kingssaga.game.model.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.factories.FactoryManager;

import java.util.Random;

/**
 * Represents a coin pouch item in the game.
 * Extends the Item class.
 */
public class CoinPouch extends Item {
    private final Random RANDOM = new Random();

    /**
     * Constructs a CoinPouch object with the given GameManager.
     * 
     * @param fm The GameManager object associated with the coin pouch.
     */
    public CoinPouch(FactoryManager fm, Sprite sprite) {
        super(fm, "Coin Pouch", 0, sprite);
    }

    /**
     * Returns the number of coins in the coin pouch.
     * 
     * @return The number of coins.
     */
    public int getCoins() {
        return getValue();
    }

    /**
     * Adds the specified amount of coins to the coin pouch.
     * If the resulting amount is negative, nothing happens.
     * 
     * @param amount The amount of coins to add.
     */
    public void addCoins(int amount) {
        if (amount >= 0) {
            setValue(this.value + amount);
        } else {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    /**
     * Attempts to remove the specified amount of coins from the coin pouch.
     * If the amount is greater than the number of coins in the pouch, does not
     * remove any coins and returns false.
     * Otherwise, removes the coins and returns true.
     * 
     * @param amount The amount of coins to remove.
     * @return true if the coins were successfully removed, false otherwise.
     */
    public boolean removeCoins(int amount) {
        if (amount > this.value) {
            return false;
        } else {
            this.value -= amount;
            return true;
        }
    }

    /**
     * Fills the coin pouch with a random amount of coins within the specified
     * range. Corrects the range if the min value is greater than the max value.
     * Throws an IllegalArgumentException if the min or max values are negative.
     * 
     * @param min The minimum amount of coins.
     * @param max The maximum amount of coins.
     */
    public void fillWithRandomAmount(int min, int max) {
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Min and max must be positive");
        }

        int lower = Math.min(min, max);
        int upper = Math.max(min, max);

        int amount = RANDOM.nextInt(upper - lower + 1) + lower;
        setValue(amount);
    }
}
