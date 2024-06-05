package com.kingssaga.game.view;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kingssaga.game.Constants;

public class TiledMapLayerWrapper implements Renderable {

    private final TiledMapTileLayer tileLayer;
    private final float z;

    /**
     * Constructs a TiledMapLayerWrapper object with the given TiledMapTileLayer.
     * The z-coordinate of the layer is calculated based on the "z" property of the tile layer.
     * If the "z" property is not set, the default z-coordinate is 0.
     *
     * @param tileLayer the TiledMapTileLayer to wrap
     */
    public TiledMapLayerWrapper(TiledMapTileLayer tileLayer) {
        this.tileLayer = tileLayer;

        Object tileNumber = tileLayer.getProperties().get("z");
        z = tileNumber == null ? 0 :  -(((tileLayer.getHeight() - Integer.parseInt(tileNumber.toString()) - 0.4f) * tileLayer.getTileHeight()) / Constants.PPM);
    }

    /**
     * Returns the z-coordinate of the layer.
     *
     * @return the z-coordinate of the layer
     */
    @Override
    public float getZ() {
        return this.z;
    }

    /**
     * Returns the wrapped TiledMapTileLayer.
     *
     * @return the wrapped TiledMapTileLayer
     */
    public TiledMapTileLayer getTileLayer() {
        return this.tileLayer;
    }
}