package com.kingssaga.game.model.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.kingssaga.game.controller.ai.Box2DLocation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.actors.enemies.Boss;
import com.kingssaga.game.model.actors.enemies.SimpleEnemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.attacks.ChargeAttack;
import com.kingssaga.game.model.attacks.SlamAttack;
import com.kingssaga.game.model.items.weapons.Weapon;

public class MeleeWeaponFactoryTest {
    
    @Mock
    private GameManager manager;
    @Mock
    private Texture texture;
    @Mock
    private Sprite sprite;
    @Mock
    GraphicsFactory graphicsFactory;
    @Mock
    AttackFactory attackFactory;
    @Mock
    ChargeAttack chargeAttack;
    @Mock
    SlamAttack slamAttack;
    @Mock
    SoundPlayer sp;
    @Mock
    Boss boss;
    @Mock  
    SimpleEnemy enemy;
    @Mock
    Player player;
    
    private MeleeWeaponFactory meleeWeaponFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(manager.getGraphicsFactory()).thenReturn(graphicsFactory);
        when(graphicsFactory.getNewSprite(any(Texture.class))).thenReturn(sprite);
        when(graphicsFactory.getNewTexture(anyString())).thenReturn(texture);
        when(manager.getAttackFactory()).thenReturn(attackFactory);
        when(attackFactory.createChargeAttack(any(), any())).thenReturn(chargeAttack);
        when(attackFactory.createSlamAttack(any())).thenReturn(slamAttack);

        meleeWeaponFactory = new MeleeWeaponFactory(manager, sp);
    }

    @Test
    public void testCreateWeapon() {
        Weapon weapon = meleeWeaponFactory.createWeapon("sword", new Vector2(1f, 1f), 1, 1);
        assertNotNull(weapon);
        assertEquals(weapon.getName(), "sword");
        assertEquals(weapon.getRange(), new Vector2(1f, 1f));
        assertEquals(weapon.getDamage(), 1);
        assertEquals(weapon.getDelay(), 1);
    }

    @Test
    public void testGiveSpawnWeaponBoss() {
        Box2DLocation location = Mockito.mock(Box2DLocation.class);
        meleeWeaponFactory.giveSpawnWeapon(boss, location);
        verify(boss, times(1)).setWieldedWeapon(any());
    }

    @Test
    public void testGiveSpawnWeaponSimpleEnemy() {
        meleeWeaponFactory.giveSpawnWeapon(enemy);
        verify(enemy, times(1)).setWieldedWeapon(any());
    }

    @Test
    public void testGiveSpawnWeaponPlayer() {
        meleeWeaponFactory.giveSpawnWeapon(player);
        verify(player, times(1)).setWieldedWeapon(any());
    }
}