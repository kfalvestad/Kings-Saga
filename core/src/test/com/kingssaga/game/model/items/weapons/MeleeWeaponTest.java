package com.kingssaga.game.model.items.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.actors.enemies.Enemy;
import com.kingssaga.game.model.actors.enemies.SimpleEnemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.attacks.Attack;
import com.kingssaga.game.model.attacks.SwingAttack;
import com.kingssaga.game.model.factories.FactoryManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MeleeWeaponTest {
    private Weapon weapon;

    @Mock
    private FactoryManager fm;

    @Mock
    private SoundPlayer sp;

    @Mock
    private SwingAttack mockSwingAttack;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String name = "Sword";
        Vector2 range = new Vector2(1, 1);
        int damage = 10;
        int delay = 2;
        Sprite sprite = new Sprite();

        weapon = new MeleeWeapon(fm, name, range, damage, delay, sprite, sp);
        weapon.setPrimaryAttack(mockSwingAttack);
        weapon.setSecondaryAttack(mockSwingAttack);
    }

    @Test
    void testGetDamage() {
        int expectedDamage = 10;
        int actualDamage = weapon.getDamage();
        assertEquals(expectedDamage, actualDamage);
    }

    @Test
    void testSetDamage() {
        int newDamage = 20;
        weapon.setDamage(newDamage);
        int actualDamage = weapon.getDamage();
        assertEquals(newDamage, actualDamage);
    }

    @Test
    void testSetDamageWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> weapon.setDamage(-10));
    }

    @Test
    void testGetDelay() {
        int expectedDelay = 2;
        int actualDelay = weapon.getDelay();
        assertEquals(expectedDelay, actualDelay);
    }

    @Test
    void testSetDelay() {
        int newDelay = 3;
        weapon.setDelay(newDelay);
        int actualDelay = weapon.getDelay();
        assertEquals(newDelay, actualDelay);
    }

    @Test
    void testSetDelayWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> weapon.setDelay(-1));
    }

    @Test
    void testGetRange() {
        Vector2 expectedRange = new Vector2(1, 1);
        Vector2 actualRange = weapon.getRange();
        assertEquals(expectedRange, actualRange);
    }

    @Test
    void testSetPrimaryAttack() {
        Attack primaryAttack = mock(Attack.class);
        weapon.setPrimaryAttack(primaryAttack);
        assertEquals(primaryAttack, weapon.getPrimaryAttack());
    }

    @Test
    void testSetSecondaryAttack() {
        Attack secondaryAttack = mock(Attack.class);
        weapon.setSecondaryAttack(secondaryAttack);
        assertEquals(secondaryAttack, weapon.getSecondaryAttack());
    }

    @Test
    void testIsAttacking() {
        assertFalse(weapon.isAttacking());
    }

    @Test
    void flipAttacks() {
        SwingAttack primaryAttack = mock(SwingAttack.class);
        SwingAttack secondaryAttack = mock(SwingAttack.class);
        weapon.setPrimaryAttack(primaryAttack);
        weapon.setSecondaryAttack(secondaryAttack);

        weapon.flipAttacks();

        verify(primaryAttack).flipSprite();
        verify(secondaryAttack).flipSprite();
    }

    @Test
    void flipAttacksWithNullAttacks() {
        weapon.setPrimaryAttack(null);
        weapon.setSecondaryAttack(null);
        weapon.flipAttacks();
        verifyNoInteractions(mockSwingAttack);
    }

    @Test
    void testDoPrimaryAttack() {
        Vector2 position = new Vector2(1, 1);
        weapon.doPrimaryAttack(position);
        verify(mockSwingAttack).performAttack(position);
        verify(sp).playSound(mockSwingAttack.getSound());
    }

    @Test
    void testDoPrimaryAttackWithNullAttack() {
        weapon.setPrimaryAttack(null);
        Vector2 position = new Vector2(1, 1);
        weapon.doPrimaryAttack(position);
    }

    @Test
    void testDoSecondaryAttack() {
        Vector2 position = new Vector2(1, 1);
        weapon.doSecondaryAttack(position);
        verify(weapon.getSecondaryAttack()).performAttack(position);
        verify(sp).playSound(weapon.getSecondaryAttack().getSound());
    }

    @Test
    void testDoSecondaryAttackWithNullAttack() {
        weapon.setSecondaryAttack(null);
        Vector2 position = new Vector2(1, 1);
        weapon.doSecondaryAttack(position);
    }

    @Test
    void meleeWeaponConstructorWithActor() {
        Player player = mock(Player.class);
        MeleeWeapon meleeWeapon = new MeleeWeapon(fm, player, "Sword", new Vector2(1, 1), 10, 2, new Sprite(), sp);
        assertEquals(player, meleeWeapon.getOwner());
    }

    @Test
    void meleeWeaponConstructorWithoutActor() {
        MeleeWeapon meleeWeapon = new MeleeWeapon(fm, "Sword", new Vector2(1, 1), 10, 2, new Sprite(), sp);
        assertNull(meleeWeapon.getOwner());
    }

    @Test
    void testGetMaskBits() {
        Player player = mock(Player.class);
        weapon.setOwner(player);
        assertEquals(Constants.ENEMY_HITBOX_BIT, weapon.getMaskBits());

        Enemy enemy = mock(SimpleEnemy.class);
        weapon.setOwner(enemy);
        assertEquals(Constants.PLAYER_HITBOX_BIT, weapon.getMaskBits());
    }

    @Test
    void testDrawAttack() {
        SwingAttack primaryAttack = mock(SwingAttack.class);
        SwingAttack secondaryAttack = mock(SwingAttack.class);
        weapon.setPrimaryAttack(primaryAttack);
        weapon.setSecondaryAttack(secondaryAttack);

        // Set isActive to true before calling drawAttack
        when(primaryAttack.isActive()).thenReturn(true);
        when(secondaryAttack.isActive()).thenReturn(true);

        SpriteBatch batch = mock(SpriteBatch.class);
        weapon.drawAttack(batch);

        verify(primaryAttack).draw(batch);
        verify(secondaryAttack).draw(batch);
    }

    @Test
    void testDrawAttackWithInactiveAttacks() {
        SwingAttack primaryAttack = mock(SwingAttack.class);
        SwingAttack secondaryAttack = mock(SwingAttack.class);
        weapon.setPrimaryAttack(primaryAttack);
        weapon.setSecondaryAttack(secondaryAttack);

        // Set isActive to false before calling drawAttack
        when(primaryAttack.isActive()).thenReturn(false);
        when(secondaryAttack.isActive()).thenReturn(false);

        SpriteBatch batch = mock(SpriteBatch.class);
        weapon.drawAttack(batch);

        verify(primaryAttack, times(0)).draw(batch);
        verify(secondaryAttack, times(0)).draw(batch);
    }

    @Test
    void playSoundTest() {
        String sound = "sound.mp3";
        weapon.playSound(sound);
        verify(sp).playSound(sound);
    }
}
