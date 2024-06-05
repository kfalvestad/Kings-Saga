package com.kingssaga.game.model;

import com.kingssaga.game.GameState;
import com.kingssaga.game.model.actors.enemies.Enemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.view.RenderOrder;
import com.kingssaga.game.view.screens.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestableGameManager extends GameManager {
    public TestableGameManager(GameScreen screen) {
        super(screen);
    }

    @Override
    protected void setUp() {
    }

    public Player getPlayer() {
        return player;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }

    protected void setRenderOrder(RenderOrder ro) {
        this.ro = ro;
    }
}


class GameManagerTest {
    private TestableGameManager manager;
    private GameScreen screen;

    @Mock
    private Item mockItem;
    @Mock
    private Enemy mockEnemy;
    @Mock
    private Player mockPlayer;
    @Mock
    private RenderOrder mockRenderOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        screen = Mockito.mock(GameScreen.class);
        manager = new TestableGameManager(screen);
        manager.setPlayer(mockPlayer);
        manager.setRenderOrder(mockRenderOrder);
    }

    @Test
    void updateShouldSetStateToGameOverWhenPlayerIsNotAlive() {
        when(manager.getPlayer().isAlive()).thenReturn(false);

        manager.update();
        verify(screen).setState(GameState.GAME_OVER);
    }

    @Test
    void updateShouldUpdatePlayerWhenPlayerIsAlive() {
        when(manager.getPlayer().isAlive()).thenReturn(true);

        manager.update();

        verify(manager.getPlayer()).update();
    }

    @Test
    void handlePlayerContactShouldAddItemToPlayerInventoryWhenPlayerContactsItem() {
        when(mockItem.handleInteraction(manager.getPlayer())).thenReturn(true);

        manager.handlePlayerContact(mockItem);

        assertTrue(manager.getBox2DFactory().getBodiesToBeDestroyed().containsKey(mockItem.getBody()));
    }

    @Test
    void updateEnemiesShouldRemoveDeadEnemies() {
        manager.getActorFactory().getEnemies().add(mockEnemy);
        when(mockEnemy.isAlive()).thenReturn(false);

        manager.updateEnemies();

        assertTrue(manager.getActorFactory().getEnemies().isEmpty());
    }

    @Test
    void updateEnemiesShouldUpdateAliveEnemies() {
        manager.getActorFactory().getEnemies().add(mockEnemy);
        when(mockEnemy.isAlive()).thenReturn(true);

        manager.updateEnemies();

        verify(mockEnemy).update();
    }

    @Test
    void handleEndNPCContactTest() {
        manager.handleEndNPCContact();
        verify(screen).hideDialogue();
    }

@Test
    void testGetWeaponFactory() {
        assertNotNull(manager.getWeaponFactory());
    }

    @Test
    void testGetLootFactory() {
        assertNotNull(manager.getLootFactory());
    }

    @Test
    void testGetItemFactory() {
        assertNotNull(manager.getItemFactory());
    }

    @Test
    void testGetGameObjectFactory() {
        assertNotNull(manager.getGameObjectFactory());
    }

    @Test
    void testGetDoors() {
        Door door = Mockito.mock(Door.class);
        manager.getDoors().add(door);
        assertTrue(manager.getDoors().contains(door));
    }

    @Test
    void testGetSoundPlayer() {
        assertEquals(manager.getSoundPlayer(), screen);
    }

    @Test
    void testMovePlayer() {
        manager.movePlayer(true, true, false, true);
        verify(mockPlayer).move(true, true, false, true);
    }

    @Test
    void testPlayerAttack() {
        manager.playerAttack();
        verify(mockPlayer).attack();
    }

    @Test
    void testGetPLayerStats() {
        assertNotNull(manager.getPlayerStats());
    }
}