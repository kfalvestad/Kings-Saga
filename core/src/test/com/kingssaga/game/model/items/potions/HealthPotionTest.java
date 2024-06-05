package com.kingssaga.game.model.items.potions;

import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.factories.FactoryManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.kingssaga.game.model.items.Consumable.EffectType;

class HealthPotionTest {

    private FactoryManager factoryManager;
    private Sprite sprite;
    private Player player;
    private HealthPotion healthPotion;

    @BeforeEach
    void setUp() {
        factoryManager = mock(FactoryManager.class);
        sprite = mock(Sprite.class);
        player = mock(Player.class);
        healthPotion = new HealthPotion(factoryManager, 10, sprite);
    }

    @Test
    void consumeEffect_shouldRestoreHealthToPlayer() {
        healthPotion.consumeEffect(player);
        verify(player).restoreHealth(10);
    }

    @Test
    void getEffectType_shouldReturnPermanent() {
        assertEquals(EffectType.PERMANENT, healthPotion.getEffectType());
    }

    @Test
    void handleInteraction_shouldConsumeItemAndReturnTrue() {
        boolean result = healthPotion.handleInteraction(player);
        verify(player).consumeItem(healthPotion);
        assertEquals(true, result);
    }
}
