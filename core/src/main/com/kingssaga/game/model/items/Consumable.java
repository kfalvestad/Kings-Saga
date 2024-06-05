package com.kingssaga.game.model.items;

import com.kingssaga.game.model.actors.player.Player;

/**
 * Interface for items that can be consumed by the player.
 */
public interface Consumable {
    public enum EffectType {
        TEMPORARY,
        PERMANENT
    }

    /**
     * When the player consumes the item, this method is called to apply
     * the effect of the item to the player.
     * In the future, the parameter could be Actor instead of Player, 
     * to allow for consumable items that affect enemies.
     * @param player The player that consumes the item.
     */
    void consumeEffect(Player player);

    /**
     * Returns the type of effect that the consumable item has.
     * @return the type of effect
     */
    EffectType getEffectType();
}
