package com.kingssaga.game.model.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.attacks.ChargeAttack;
import com.kingssaga.game.model.attacks.SlamAttack;
import com.kingssaga.game.model.attacks.SwingAttack;
import com.kingssaga.game.model.items.weapons.MeleeWeapon;
import com.kingssaga.game.model.items.weapons.Weapon;

public class AttackFactoryTest {

    @Mock
    private GameManager manager;
    @Mock
    private Texture texture;
    @Mock
    private Sprite sprite;
    @Mock
    GraphicsFactory graphicsFactory;
    @Mock
    WeaponFactory weaponFactory;
    @Mock
    Box2DFactory b2df;
    @Mock
    SoundPlayer sp;
    @Mock
    Weapon weapon;
    @Mock
    Body body;
    @Mock
    Actor owner;

    private AttackFactory attackFactory;

    @BeforeEach
    public void setUp() {
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

        when(manager.getGraphicsFactory()).thenReturn(graphicsFactory);
        when(graphicsFactory.getNewSprite(any(Texture.class))).thenReturn(sprite);
        when(graphicsFactory.getNewTexture(anyString())).thenReturn(texture);
        when(manager.getWeaponFactory()).thenReturn(weaponFactory);
        when(manager.getBox2DFactory()).thenReturn(b2df);
        when(weaponFactory.createWeapon(anyString(), any(), anyInt(), anyInt()))
                .thenReturn(new MeleeWeapon(manager, "sword", new Vector2(1f, 1f), 1, 1, sprite, sp));

        weapon = weaponFactory.createWeapon("sword", new Vector2(1f, 1f), 1, 1);

        attackFactory = new AttackFactory(manager);
    }

    @Test
    public void testGetNewAttack_ChargeAttack() {
        Box2DLocation location = Mockito.mock(Box2DLocation.class);
        assertTrue(attackFactory.createChargeAttack(weapon, location) instanceof ChargeAttack);
    }

    @Test
    public void testGetNewAttack_SwingAttack() {
        assertTrue(attackFactory.createSwingAttack(weapon) instanceof SwingAttack);
    }

    @Test
    public void testSlamAttack() {
        assertTrue(attackFactory.createSlamAttack(weapon) instanceof SlamAttack);
    }


}
