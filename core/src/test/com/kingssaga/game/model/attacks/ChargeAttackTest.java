package com.kingssaga.game.model.attacks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;
import com.kingssaga.game.model.actors.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ChargeAttackTest {

    ChargeAttack chargeAttack;
    PolygonShape shape;
    @Mock
    Weapon weapon;
    @Mock
    Box2DFactory b2df;
    @Mock
    Sprite sprite;
    @Mock
    Actor owner;
    @Mock
    SpriteBatch batch;
    @Mock
    Body body;
    @Mock
    Box2DLocation location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        World world = new World(new Vector2(0, -9.8f), true);
        BodyDef bodyDef = new BodyDef();
        shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body2 = world.createBody(bodyDef);
        fixtureDef.shape = shape;
        Fixture fixture = body2.createFixture(fixtureDef);

        when(b2df.getNewFixtureDef()).thenReturn(fixtureDef);
        when(b2df.getNewBodyDef()).thenReturn(bodyDef);
        when(b2df.getNewPolygonShape()).thenReturn(shape);
        when(b2df.createBody(bodyDef)).thenReturn(body);
        when(body.createFixture(fixtureDef)).thenReturn(fixture);
        when(body.getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getOwner()).thenReturn(owner);
        when(weapon.getOwner().getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getRange()).thenReturn(new Vector2(2, 2));
        when(weapon.getDamage()).thenReturn(1);
        when(location.getPosition()).thenReturn(new Vector2(1, 1));

        chargeAttack = new ChargeAttack(weapon, b2df, sprite, location);
    }

    @Test
    void testPerformAttack() {
        // Arrange
        Vector2 position = new Vector2(1, 1);

        // Act
        chargeAttack.performAttack(position);

        // Assert
        assertTrue(chargeAttack.isActive);
        assertEquals(100, chargeAttack.swingTimer);
        verify(weapon.getOwner()).setVelocity(any(Vector2.class), eq(10));
    }

    @Test
    void getSoundTest() {
        // Act
        String sound = chargeAttack.getSound();

        // Assert
        assertEquals("axe.mp3", sound);
    }

    // TODO: A lot of issues related to testing this method
    @Test
    void testDraw() {
        // Test when swingTimer is 0
        chargeAttack.swingTimer = 0;
        assertEquals(0, chargeAttack.swingTimer);
        chargeAttack.draw(batch);
        verify(sprite, times(1)).draw(batch);
    }
    
    @Test
    void flipSpriteTest() {
        assertFalse(chargeAttack.isFlipped());
        chargeAttack.flipSprite();
        assertTrue(chargeAttack.isFlipped());
    }

    @Test
    void getDamageTest() {
        int damage = chargeAttack.getDamage();

        // ChargeAttack damageModifier is 1
        assertEquals(1, damage);
    }

    @Test
    void setTargetTest() {
        Vector2 target = new Vector2(1, 1);
        chargeAttack.setTarget(target);
        assertEquals(target, chargeAttack.target);
    }

    @Test
    void isActiveTest() {
        assertFalse(chargeAttack.isActive());
    }

    @Test
    void setIsActiveTest() {
        chargeAttack.setIsActive(true);
        assertTrue(chargeAttack.isActive());
    }

}