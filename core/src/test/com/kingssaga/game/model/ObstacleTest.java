package com.kingssaga.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.model.factories.Box2DFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    private Obstacle obstacle;

    @BeforeEach
    void setUp() {
        Box2DFactory b2df = new Box2DFactory();
        Rectangle rect = new Rectangle(0, 0, 200, 200);
        obstacle = new Obstacle(b2df, rect);
    }

    @Test
    void getLinearVelocity() {
        assertEquals(0, obstacle.getLinearVelocity().x);
        assertEquals(0, obstacle.getLinearVelocity().y);
    }

    @Test
    void getAngularVelocity() {
        assertEquals(0, obstacle.getAngularVelocity());
    }

    @Test
    void getBoundingRadius() {
        assertEquals(0, obstacle.getBoundingRadius());
    }

    @Test
    void isTagged() {
        assertFalse(obstacle.isTagged());
    }

    @Test
    void setTagged() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getZeroLinearSpeedThreshold() {
        assertEquals(0, obstacle.getZeroLinearSpeedThreshold());
    }

    @Test
    void setZeroLinearSpeedThreshold() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getMaxLinearSpeed() {
        assertEquals(0, obstacle.getMaxLinearSpeed());
    }

    @Test
    void setMaxLinearSpeed() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getMaxLinearAcceleration() {
        assertEquals(0, obstacle.getMaxLinearAcceleration());
    }

    @Test
    void setMaxLinearAcceleration() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getMaxAngularSpeed() {
        assertEquals(0, obstacle.getMaxAngularSpeed());
    }

    @Test
    void setMaxAngularSpeed() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getMaxAngularAcceleration() {
        assertEquals(0, obstacle.getMaxAngularAcceleration());
    }

    @Test
    void setMaxAngularAcceleration() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void getPosition() {
        assertEquals(1, obstacle.getPosition().x);
        assertEquals(1, obstacle.getPosition().y);
    }

    @Test
    void getOrientation() {
        assertEquals(0, obstacle.getOrientation());
    }

    @Test
    void setOrientation() {
        assertThrows(UnsupportedOperationException.class, () -> obstacle.setTagged(true));
    }

    @Test
    void vectorToAngle() {
        assertEquals(0, obstacle.vectorToAngle(new Vector2(1, 0)));
    }

    @Test
    void angleToVector() {
        assertNull(obstacle.angleToVector(new Vector2(), 0));
    }

    @Test
    void newLocation() {
        assertNull(obstacle.newLocation());
    }
}