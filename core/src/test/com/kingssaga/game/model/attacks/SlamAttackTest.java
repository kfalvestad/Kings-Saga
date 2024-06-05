package com.kingssaga.game.model.attacks;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;
import com.kingssaga.game.model.actors.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SlamAttackTest {
    SlamAttack slamAttack;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        World world = new World(new Vector2(0, -9.8f), true);
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body2 = world.createBody(bodyDef);
        fixtureDef.shape = shape;
        Fixture fixture = body2.createFixture(fixtureDef);
        Texture texture = mock(Texture.class);
        // Texture texture = new Texture("groundslam.png");
        Sprite spriteReal = new Sprite(texture);

        when(b2df.getNewFixtureDef()).thenReturn(fixtureDef);
        when(b2df.getNewBodyDef()).thenReturn(bodyDef);
        when(b2df.getNewPolygonShape()).thenReturn(shape);
        when(b2df.createBody(bodyDef)).thenReturn(body);
        when(body.createFixture(fixtureDef)).thenReturn(fixture);
        when(body.getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getOwner()).thenReturn(owner);
        when(weapon.getOwner().getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getRange()).thenReturn(new Vector2(2, 2));

        slamAttack = new SlamAttack(weapon, b2df, spriteReal);
    }

    @Test
    void testPerformAttack() {
        slamAttack.performAttack(new Vector2(1, 1));
        assertTrue(slamAttack.isActive);
        verify(b2df).createBody(any(BodyDef.class));
        verify(b2df).removeBodyAfterDelay(any(Body.class), anyInt());
    }
    
    @Test
    void testPerformAttackWithTarget() {
        slamAttack.performAttack(new Vector2(1, 1));
        assertTrue(slamAttack.isActive);
        verify(b2df).createBody(any(BodyDef.class));
        verify(b2df).removeBodyAfterDelay(any(Body.class), anyInt());
    }

    @Test
    void testGetSound() {
        assertEquals("slam.mp3", slamAttack.getSound());
    }

}
