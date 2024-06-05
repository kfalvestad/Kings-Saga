package com.kingssaga.game.model.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.kingssaga.game.controller.ai.Box2DSteeringEntity;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Item;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

class ActorFactoryTest {

    @Mock
    private SoundPlayer sp;
    @Mock
    private FactoryManager manager;
    @Mock
    WeaponFactory weaponFactory;
    @Mock
    ItemFactory itemFactory;
    @Mock
    GraphicsFactory graphicsFactory;
    @Mock
    Box2DFactory b2df;
    @Mock
    RandomLootFactory lootFactory;
    @Mock
    Texture texture;
    @Mock
    Sprite sprite;
    @Mock
    Body body;
    @Mock
    Box2DSteeringEntity steering;
    // @Mock
    CoinPouch coinPouch;

    PolygonShape shape;
    BodyDef bodyDef = new BodyDef();
    ActorFactory actorFactory;

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
        coinPouch = new CoinPouch(manager, sprite);

        when(b2df.getNewFixtureDef()).thenReturn(fixtureDef);
        when(b2df.getNewBodyDef()).thenReturn(bodyDef);
        when(b2df.getNewPolygonShape()).thenReturn(shape);
        when(b2df.createBody(bodyDef)).thenReturn(body);
        when(b2df.createBody(any(),any())).thenReturn(body2);
        when(body.createFixture(fixtureDef)).thenReturn(fixture);
        when(body.getPosition()).thenReturn(new Vector2(1, 1));
        when(manager.getWeaponFactory()).thenReturn(weaponFactory);
        when(manager.getItemFactory()).thenReturn(itemFactory);
        when(manager.getGraphicsFactory()).thenReturn(graphicsFactory);
        when(manager.getLootFactory()).thenReturn(lootFactory);
        when(lootFactory.createLoot(any(), anyInt(), anyInt())).thenReturn(new ArrayList<Item>(Arrays.asList(coinPouch)));
        when(b2df.getNewSteeringEntity(any(), anyFloat())).thenReturn(steering);
        when(graphicsFactory.getNewTexture(anyString())).thenReturn(texture);
        when(graphicsFactory.getNewSprite(texture)).thenReturn(sprite);
        when(manager.getBox2DFactory()).thenReturn(b2df);
        // when(body.createFixture(fixtureDef)).thenReturn(fixture);

        actorFactory = new ActorFactory(manager, sp);
    }

    @Test
    void getEnemiesTest() {
        assertEquals(actorFactory.getEnemies().size(), 0);
    }


}