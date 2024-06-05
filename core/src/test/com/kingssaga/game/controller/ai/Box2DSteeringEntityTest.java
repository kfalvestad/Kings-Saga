package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

/**
 * Some of the tests were created with the help of chat GPT
 */
public class Box2DSteeringEntityTest {

    @Mock
    private Body mockBody;

    private Box2DSteeringEntity entity;
    private float boundingRadius = 10.0f;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(5, 5));
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(2, 2));
        when(mockBody.getAngularVelocity()).thenReturn(1.0f);

        entity = new Box2DSteeringEntity(mockBody, boundingRadius);
    }

    @Test
    public void constructorTest() {
        assertEquals(boundingRadius, entity.getBoundingRadius(), 0.0f);
        assertFalse(entity.isTagged());
    }

    @Test
    public void getLinearVelocityTest() {
        Vector2 velocity = entity.getLinearVelocity();
        assertNotNull(velocity);
        assertEquals(2, velocity.x, 0.0f);
        assertEquals(2, velocity.y, 0.0f);
    }

    @Test
    public void getAngularVelocityTest() {
        assertEquals(1.0f, entity.getAngularVelocity(), 0.0f);
    }

    @Test
    public void getBoundingRadiusTest() {
        assertEquals(boundingRadius, entity.getBoundingRadius(), 0.0f);
    }

    @Test
    public void isTaggedTest() {
        assertFalse(entity.isTagged());
    }

    @Test
    public void setTaggedTest() {
        entity.setTagged(true);
        assertTrue(entity.isTagged());
    }

    @Test
    public void getZeroLinearSpeedThresholdTest() {
        assertEquals(0, entity.getZeroLinearSpeedThreshold(), 0.0f);
    }

    @Test
    public void testSetZeroLinearSpeedThreshold() {
        assertThrows(UnsupportedOperationException.class, () -> entity.setZeroLinearSpeedThreshold(0));
    }

    @Test
    public void getMaxLinearSpeedTest() {
        assertEquals(0.0f, entity.getMaxLinearSpeed(), 0.0f);
    }

    @Test
    public void setMaxLinearSpeedTest() {
        entity.setMaxLinearSpeed(10.0f);
        assertEquals(10.0f, entity.getMaxLinearSpeed(), 0.0f);
    }

    @Test
    public void getMaxLinearAccelerationTest() {
        assertEquals(0.0f, entity.getMaxLinearAcceleration(), 0.0f);
    }

    @Test
    public void setMaxLinearAccelerationTest() {
        entity.setMaxLinearAcceleration(10.0f);
        assertEquals(10.0f, entity.getMaxLinearAcceleration(), 0.0f);
    }

    @Test
    public void getMaxAngularSpeedTest() {
        assertThrows(UnsupportedOperationException.class, () -> entity.getMaxAngularSpeed());
    }
    
    @Test
    public void setMaxAngularSpeedTest() {
        assertThrows(UnsupportedOperationException.class, () -> entity.setMaxAngularSpeed(0.0f));
    }

    @Test
    public void getMaxAngularAccelerationTest() {
        assertEquals(1f, entity.getMaxAngularAcceleration());
        // assertThrows(UnsupportedOperationException.class, () -> entity.getMaxAngularAcceleration());
    }

    @Test
    public void setMaxAngularAccelerationTest() {
        assertThrows(UnsupportedOperationException.class, () -> entity.setMaxAngularAcceleration(10.0f));
    }

    @Test
    public void getPositionTest() {
        Vector2 position = entity.getPosition();
        assertNotNull(position);
        assertEquals(5, position.x, 0.0f);
        assertEquals(5, position.y, 0.0f);
    }

    @Test
    public void getOrientationTest() {
        assertEquals(0.0f, entity.getOrientation(), 0.0f);
    }

    @Test
    public void setOrientationTest() {
        assertThrows(UnsupportedOperationException.class, () -> entity.setOrientation(0.0f));
    }

    @Test
    public void vectorToAngleTest() {
        assertEquals(0.0f, entity.vectorToAngle(new Vector2(1, 0)), 0.0f);
    }

    @Test
    public void angleToVectorTest() {
        Vector2 vector = new Vector2(1, 1);
        assertNull(entity.angleToVector(vector, 0.0f));
    }

    @Test
    public void newLocationTest() {
        assertNull(entity.newLocation());
    }
}