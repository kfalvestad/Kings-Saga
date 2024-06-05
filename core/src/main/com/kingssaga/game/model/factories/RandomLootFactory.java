package com.kingssaga.game.model.factories;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.ItemType;

public class RandomLootFactory implements LootFactory {
    private final Random RANDOM = new Random();
    private final FactoryManager fm;

    public RandomLootFactory(FactoryManager fm) {
        this.fm = fm;
    }

    @Override
    public List<Item> createLoot(List<ItemType> availableItems, int minAmount, int maxAmount) {
        if (availableItems == null || availableItems.isEmpty()) {
            throw new IllegalArgumentException("No available items to create loot from");
        }
        if (minAmount < 0 || maxAmount < 0) {
            throw new IllegalArgumentException("Amount of loot must be non-negative");
        } 
        if (minAmount == 0 && maxAmount == 0) {
            throw new IllegalArgumentException("Amount of loot must be positive");
        }

        ItemFactory itemFactory = fm.getItemFactory();
        List<Item> loot = new ArrayList<>();
        int lowerBound = Math.min(minAmount, maxAmount);
        int upperBound = Math.max(minAmount, maxAmount);
        int amount = RANDOM.nextInt(upperBound - lowerBound + 1) + lowerBound;

        for (int i = 0; i < amount; i++) {
            ItemType itemEnum = availableItems.get(RANDOM.nextInt(availableItems.size()));
            Item item = itemFactory.getNewItem(itemEnum);
            loot.add(item);
        }
        return loot;
    }

}
