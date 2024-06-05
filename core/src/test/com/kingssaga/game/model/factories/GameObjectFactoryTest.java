package com.kingssaga.game.model.factories;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.Door;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;

class GameObjectFactoryTest {

    @Mock
    private GameManager manager;

    private GameObjectFactory gameObjectFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameObjectFactory = new GameObjectFactory(manager);
    }

    @Test
    void testCreateDoor() {
        // Arrange
        int ID = 1;
        RectangleMapObject object = Mockito.mock(RectangleMapObject.class);
        Rectangle rectangle = Mockito.mock(Rectangle.class);
        when(object.getRectangle()).thenReturn(rectangle);

        Door door = Mockito.mock(Door.class);
        when(door.getID()).thenReturn(ID);
        when(door.isLocked()).thenReturn(true);
        when(manager.getDoors()).thenReturn(Collections.singletonList(door));

        // Act
        gameObjectFactory.createDoor(ID, object);

        // Assert
        verify(door, times(1)).createBody();
        verify(manager, times(1)).giveKey(ID);
        verify(manager, never()).addDoor(any(Door.class));
    }

    @Test
    void testCreateDoorWillCreate() {
        // Arrange
        int ID = 1;
        RectangleMapObject object = Mockito.mock(RectangleMapObject.class);
        Rectangle rectangle = Mockito.mock(Rectangle.class);
        when(object.getRectangle()).thenReturn(rectangle);

        Door door = Mockito.mock(Door.class);
        when(door.getID()).thenReturn(ID);
        when(door.isLocked()).thenReturn(false);
        when(manager.getDoors()).thenReturn(Collections.singletonList(door));

        // Act
        gameObjectFactory.createDoor(ID, object);

        // Assert
        verify(door, never()).createBody();
        verify(manager, never()).giveKey(ID);
        verify(manager, never()).addDoor(any(Door.class));
    }

}