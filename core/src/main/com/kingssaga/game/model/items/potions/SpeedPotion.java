package com.kingssaga.game.model.items.potions;

import com.kingssaga.game.model.factories.FactoryManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.items.Consumable;
import com.kingssaga.game.model.items.Item;

public class SpeedPotion extends Item implements Consumable {

    private final float speedMultiplier;
    private final float duration;

    public SpeedPotion(FactoryManager manager, float speedMultiplier, float duration, Sprite sprite) {
        super(manager, "Speed Potion", 10, sprite);
        this.speedMultiplier = speedMultiplier;
        this.duration = duration;
    }

    @Override
    public void consumeEffect(Player player) {
        player.boostSpeed(duration, speedMultiplier);
    }
    
    @Override   
    public EffectType getEffectType() {
        return EffectType.TEMPORARY;
    }

    @Override
    public boolean handleInteraction(Player player) {
        if (!player.isAffectedByPotion()) {
            player.consumeItem(this);
            return true;
        }
        return false;
    }
}
