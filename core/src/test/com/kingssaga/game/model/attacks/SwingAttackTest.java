package com.kingssaga.game.model.attacks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;

public class SwingAttackTest {

    SwingAttack swingAttack;
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

        when(b2df.getNewFixtureDef()).thenReturn(fixtureDef);
        when(b2df.getNewBodyDef()).thenReturn(bodyDef);
        when(b2df.getNewPolygonShape()).thenReturn(shape);
        when(b2df.createBody(bodyDef)).thenReturn(body);
        when(body.createFixture(fixtureDef)).thenReturn(fixture);
        when(body.getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getOwner()).thenReturn(owner);
        when(weapon.getOwner().getPosition()).thenReturn(new Vector2(1, 1));
        when(weapon.getRange()).thenReturn(new Vector2(2, 2));

        swingAttack = new SwingAttack(weapon, b2df, sprite);
    }

    @Test
    void testPerformAttack() {
        Vector2 position = new Vector2(1, 1);
        swingAttack.performAttack(position);
        verify(b2df).createBody(any(BodyDef.class));
        verify(b2df).removeBodyAfterDelay(any(Body.class), anyInt());
    }

    @Test
    void testPerformAttackWithTarget() {
        Vector2 position = new Vector2(1, 1);
        Vector2 target = new Vector2(2, 2);
        swingAttack.performAttack(position);
        verify(b2df).createBody(any(BodyDef.class));
        verify(b2df).removeBodyAfterDelay(any(Body.class), anyInt());
    }

    @Test
    void testGetSound() {
        // TODO: Hardcoded sounds based on SwingAttack constructor
        List<String> sounds = new ArrayList<>(Arrays.asList("swing1.mp3", "swing2.mp3", "swing3.mp3"));
        String sound = swingAttack.getSound();
        assert(sounds.contains(sound));
    }

    @Test
    void testDraw() {
        swingAttack.draw(batch);
        verify(sprite).draw(batch);
        assertFalse(swingAttack.isActive);
    }
}
