package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Box2DLocationTest {

    @Mock
    private Body mockBody;

    private Box2DLocation location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(10, 20));
        location = new Box2DLocation(mockBody);
    }

    @Test
    void getPosition_ShouldReturnCorrectPosition() {
        Vector2 position = location.getPosition();
        assertNotNull(position);
        assertEquals(10, position.x);
        assertEquals(20, position.y);
    }

    @Test
    void getPosition_ShouldNotReturnWrongPosition() {
        Vector2 position = location.getPosition();
        assertNotNull(position);
        assertNotEquals(20, position.x);
        assertNotEquals(10, position.y);
    }

    @Test
    void getOrientation_ShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> location.getOrientation());
    }

    @Test
    void setOrientation_ShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> location.setOrientation(1.0f));
    }

    @Test
    void vectorToAngle_ShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> location.vectorToAngle(new Vector2(1, 1)));
    }

    @Test
    void angleToVector_ShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> location.angleToVector(new Vector2(), 1.0f));
    }

    @Test
    void newLocation_ShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> location.newLocation());
    }
}