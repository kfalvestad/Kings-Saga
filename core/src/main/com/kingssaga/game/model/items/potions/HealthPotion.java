package com.kingssaga.game.model.items.potions;

import com.kingssaga.game.model.factories.FactoryManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.items.Consumable;
import com.kingssaga.game.model.items.Item;

public class HealthPotion extends Item implements Consumable {

    private final int healthRestored;

    public HealthPotion(FactoryManager manager, int healthRestored, Sprite sprite) {
        super(manager, "Health Potion", 10, sprite);
        this.healthRestored = healthRestored;
    }

    @Override
    public void consumeEffect(Player player) {
        player.restoreHealth(healthRestored);
    }

    @Override   
    public EffectType getEffectType() {
        return EffectType.PERMANENT;
    }

    @Override
    public boolean handleInteraction(Player player) {
        player.consumeItem(this);
        return true;
    }
}
