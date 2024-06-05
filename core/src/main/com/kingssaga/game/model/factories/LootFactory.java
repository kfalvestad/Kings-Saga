package com.kingssaga.game.model.factories;

import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.ItemType;

import java.util.List;

/**
 * The LootFactory interface represents a factory for creating loot items.
 * It provides a method to create loot based on the available item types and the desired amount range.
 */
public interface LootFactory {

    /**
     * Creates loot items based on the available item types and the desired amount range.
     *
     * @param availableItems The list of available item types.
     * @param minAmount      The minimum amount of loot items to create.
     * @param maxAmount      The maximum amount of loot items to create.
     * @return A list of created loot items.
     */
    List<Item> createLoot(List<ItemType> availableItems, int minAmount, int maxAmount);

}
