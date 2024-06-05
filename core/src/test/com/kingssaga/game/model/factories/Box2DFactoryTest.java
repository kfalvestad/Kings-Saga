package com.kingssaga.game.model.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kingssaga.game.controller.ai.Box2DSteeringEntity;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.TestActor;
import com.kingssaga.game.model.WorldContactListener;
import com.kingssaga.game.model.actors.IActor;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class Box2DFactoryTest {

    private Box2DFactory box2DFactory;

    @BeforeEach
    void setUp() {
        box2DFactory = new Box2DFactory();
    }

    @AfterEach
    void tearDown() {
        box2DFactory.dispose();
    }

    @Test
    void testGetNewBodyDef() {
        BodyDef bodyDef = box2DFactory.getNewBodyDef();
        assertNotNull(bodyDef);
    }

    @Test
    void testGetNewFixtureDef() {
        FixtureDef fixtureDef = box2DFactory.getNewFixtureDef();
        assertNotNull(fixtureDef);
    }

    @Test
    void testGetNewPolygonShape() {
        PolygonShape polygonShape = box2DFactory.getNewPolygonShape();
        assertNotNull(polygonShape);
    }

    @Test
    void testGetNewBox2DLocation() {
        Body body = box2DFactory.createBody(new BodyDef());
        Box2DLocation box2DLocation = box2DFactory.getNewBox2DLocation(body);
        assertNotNull(box2DLocation);
        assertEquals(Box2DLocation.class, box2DLocation.getClass());
    }

    @Test
    void testGetNewSteeringEntity() {
        Body body = box2DFactory.createBody(new BodyDef());
        float boundingRadius = 1.0f;
        Box2DSteeringEntity steeringEntity = box2DFactory.getNewSteeringEntity(body, boundingRadius);
        assertNotNull(steeringEntity);
        assertEquals(Box2DSteeringEntity.class, steeringEntity.getClass());
        assertEquals(boundingRadius, steeringEntity.getBoundingRadius());
    }

    @Test
    void testCreateBody() {
        BodyDef bodyDef = new BodyDef();
        Body body = box2DFactory.createBody(bodyDef);
        assertNotNull(body);
    }

    @Test
    void testCreateBodyWithActorAndPosition() {
        IActor actor = new TestActor();
        Vector2 position = new Vector2(1.0f, 2.0f);
        Body body = box2DFactory.createBody(actor, position);
        assertNotNull(body);
        assertEquals(actor.getBodyType(), body.getType());
        assertEquals(position, body.getPosition());
    }

    @Test
    void testRemoveBodyAfterDelay() {
        Body body = box2DFactory.createBody(new BodyDef());
        int delay = 100;
        box2DFactory.removeBodyAfterDelay(body, delay);
        assertTrue(box2DFactory.getBodiesToBeDestroyed().containsKey(body));
        assertEquals(delay, box2DFactory.getBodiesToBeDestroyed().get(body));
    }

    @Test
    void testDestroyBody() {
        Body body = box2DFactory.createBody(new BodyDef());
        box2DFactory.destroyBody(body);
        assertFalse(box2DFactory.getWorld().getBodyCount() > 0);
    }

    @Test
    void testGetBodies() {
        Array<Body> bodies = new Array<>();
        box2DFactory.getBodies(bodies);
        assertNotNull(bodies);
    }

    @Test
    void testStep() {
        box2DFactory.step();
    }

    @Test
    void getContactListenerTest() {
        WorldContactListener contactListener = box2DFactory.getContactListener();
        assertNotNull(contactListener);
    }
}
