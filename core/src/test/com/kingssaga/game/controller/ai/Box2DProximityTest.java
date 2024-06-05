package com.kingssaga.game.controller.ai;

import com.badlogic.gdx.ai.steer.Proximity.ProximityCallback;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.kingssaga.game.model.actors.enemies.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class Box2DProximityTest {

    @Mock
    private Steerable<Vector2> owner;
    @Mock
    private World world;
    @Mock
    private Fixture fixture;
    @Mock
    private Enemy enemy;
    @Mock
    private ProximityCallback<Vector2> callback;

    private Box2DProximity proximity;
    private final float detectionRadius = 5.0f;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(owner.getPosition()).thenReturn(new Vector2(0, 0));
        when(fixture.getUserData()).thenReturn(enemy);
        when(enemy.getSteering()).thenReturn(mock(Steerable.class));
        proximity = new Box2DProximity(owner, world, detectionRadius);
        when(callback.reportNeighbor(any())).thenReturn(true);
    }

    @Test
    void testFindNeighbors() {
        proximity.findNeighbors(callback);
        verify(world).QueryAABB(eq(proximity), eq(-detectionRadius), eq(-detectionRadius), eq(detectionRadius), eq(detectionRadius));
    }

    @Test
    void testGetOwner() {
        assertEquals(owner, proximity.getOwner());
    }

    @Test
    void testSetOwner() {
        Steerable<Vector2> newOwner = mock(Steerable.class);
        proximity.setOwner(newOwner);
        assertEquals(newOwner, proximity.getOwner());
    }

    @Test
    void testFindNeighbors_IncreasesNeighborCount_WhenEnemyFound() {
        // Mock world.QueryAABB to trigger reportFixture
        doAnswer(invocation -> {
            QueryCallback callback = invocation.getArgument(0);
            callback.reportFixture(fixture);
            return true;
        }).when(world).QueryAABB(any(), anyFloat(), anyFloat(), anyFloat(), anyFloat());

        when(callback.reportNeighbor(any())).thenReturn(true);

        int count = proximity.findNeighbors(callback);
        assertEquals(1, count);
    }

    //Ønsker også en test på reportFixture.
}