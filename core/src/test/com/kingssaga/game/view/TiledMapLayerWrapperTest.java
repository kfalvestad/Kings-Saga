package com.kingssaga.game.view;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kingssaga.game.Constants;
import com.badlogic.gdx.maps.MapProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TiledMapLayerWrapperTest {

    @Mock
    private TiledMapTileLayer mockTileLayer;
    @Mock
    private MapProperties mockProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockTileLayer.getProperties()).thenReturn(mockProperties);
    }

    @Test
    void testZValueDefault() {
        when(mockProperties.get("z")).thenReturn(null);
        TiledMapLayerWrapper layerWrapper = new TiledMapLayerWrapper(mockTileLayer);
        assertEquals(0, layerWrapper.getZ(), "Expected default z value to be 0");
    }

    @Test
    void testZValueSet() {
        when(mockProperties.get("z")).thenReturn("5");
        when(mockTileLayer.getHeight()).thenReturn(10);
        when(mockTileLayer.getTileHeight()).thenReturn(32);

        TiledMapLayerWrapper wrapper = new TiledMapLayerWrapper(mockTileLayer);
        float expectedZ = -((4.6f * 32) / Constants.PPM);

        assertEquals(expectedZ, wrapper.getZ(), "Expected calculated z value to match");
    }

    @Test
    void testGetTileLayer() {
        TiledMapLayerWrapper wrapper = new TiledMapLayerWrapper(mockTileLayer);
        assertSame(mockTileLayer, wrapper.getTileLayer(), "Expected getTileLayer to return the original tile layer");
    }
    
}
