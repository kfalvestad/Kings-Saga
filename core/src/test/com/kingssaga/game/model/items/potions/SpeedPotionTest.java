package com.kingssaga.game.model.items.potions;

import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.Consumable.EffectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpeedPotionTest {

    @Mock
    private FactoryManager mockManager;

    @Mock
    private Player mockPlayer;

    private SpeedPotion speedPotion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        speedPotion = new SpeedPotion(mockManager, 2.0f, 10.0f, null);
    }

    @Test
    public void testConsumeEffect() {
        speedPotion.consumeEffect(mockPlayer);
        verify(mockPlayer).boostSpeed(10.0f, 2.0f);
    }

    @Test
    public void testGetEffectType() {
        assertEquals(EffectType.TEMPORARY, speedPotion.getEffectType());
    }

    @Test
    public void testHandleInteraction() {
        when(mockPlayer.isAffectedByPotion()).thenReturn(false);
        assertTrue(speedPotion.handleInteraction(mockPlayer));
        verify(mockPlayer).consumeItem(speedPotion);

        when(mockPlayer.isAffectedByPotion()).thenReturn(true);
        assertFalse(speedPotion.handleInteraction(mockPlayer));
    }
}