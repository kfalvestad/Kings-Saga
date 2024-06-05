package com.kingssaga.game.model.actors;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.kingssaga.game.model.factories.Box2DFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NPCTest {

    private NPC npc;
    private Box2DFactory b2df;
    private MapObject object;

    @BeforeEach
    void setUp() {
        b2df = new Box2DFactory();
        object = Mockito.mock(RectangleMapObject.class);
        Mockito.when(object.getProperties()).thenReturn(new MapProperties());
        object.getProperties().put("name", "Test NPC");
        object.getProperties().put("message", "Hello:World");
        Mockito.when(((RectangleMapObject) object).getRectangle()).thenReturn(new Rectangle());
        npc = new NPC(b2df, object);
    }

    @Test
    void shouldSetNameFromObjectProperties() {
        npc.setName((String) object.getProperties().get("name"));
        assertEquals("Test NPC", npc.getName());
    }

    @Test
    void shouldSetMessageFromObjectProperties() {
        npc.setMessage((String) object.getProperties().get("message"));
        ArrayList<String> expectedDialogue = new ArrayList<>();
        expectedDialogue.add("Hello");
        expectedDialogue.add("World");
        assertEquals(expectedDialogue, npc.getDialogue());
    }

    @Test
    void shouldHandleEmptyMessage() {
        npc.setMessage("");
        npc.updateDialogue();
        npc.setMessage((String) object.getProperties().get("message2"));
        assertTrue(npc.getDialogue().isEmpty());
    }
}