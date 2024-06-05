package com.kingssaga.game.model;

import com.badlogic.gdx.physics.box2d.*;
import com.kingssaga.game.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class WorldContactListenerTest {

    @Mock private GameManager manager;
    @Mock private Contact contact;
    @Mock private Fixture fixtureA;
    @Mock private Fixture fixtureB;
    @InjectMocks private WorldContactListener listener;

    private Filter filterA;
    private Filter filterB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterA = new Filter();
        filterB = new Filter();
    }

    @Test
    void testBeginContact_PlayerContact() {
        when(contact.getFixtureA()).thenReturn(fixtureA);
        when(contact.getFixtureB()).thenReturn(fixtureB);
        filterA.categoryBits = Constants.PLAYER_BIT;
        filterB.categoryBits = Constants.ENEMY_BIT;
        when(fixtureA.getFilterData()).thenReturn(filterA);
        when(fixtureB.getFilterData()).thenReturn(filterB);

        Object enemy = new Object();
        when(fixtureB.getUserData()).thenReturn(enemy);

        listener.beginContact(contact);

        verify(manager).handlePlayerContact(enemy);
    }

    @Test
    void testEndContact_PlayerHitboxAndNPC() {
        when(contact.getFixtureA()).thenReturn(fixtureA);
        when(contact.getFixtureB()).thenReturn(fixtureB);
        filterA.categoryBits = Constants.PLAYER_HITBOX_BIT;
        filterB.categoryBits = Constants.NPC_BIT;
        when(fixtureA.getFilterData()).thenReturn(filterA);
        when(fixtureB.getFilterData()).thenReturn(filterB);

        listener.endContact(contact);

        verify(manager).handleEndNPCContact();
    }

    @Test
    void testBeginContact_PlayerAndItem() {
        when(contact.getFixtureA()).thenReturn(fixtureA);
        when(contact.getFixtureB()).thenReturn(fixtureB);
        filterA.categoryBits = Constants.PLAYER_BIT;
        filterB.categoryBits = Constants.ITEM_BIT;
        when(fixtureA.getFilterData()).thenReturn(filterA);
        when(fixtureB.getFilterData()).thenReturn(filterB);

        Object item = new Object();
        when(fixtureB.getUserData()).thenReturn(item);

        listener.beginContact(contact);

        verify(manager).handlePlayerContact(item);
    }

    @Test
    void testBeginContact_PlayerAndNPC() {
        when(contact.getFixtureA()).thenReturn(fixtureA);
        when(contact.getFixtureB()).thenReturn(fixtureB);
        filterA.categoryBits = Constants.PLAYER_BIT;
        filterB.categoryBits = Constants.NPC_BIT;
        when(fixtureA.getFilterData()).thenReturn(filterA);
        when(fixtureB.getFilterData()).thenReturn(filterB);

        Object npc = new Object();
        when(fixtureB.getUserData()).thenReturn(npc);

        listener.beginContact(contact);

        verify(manager).handlePlayerContact(npc);
    }

   
}