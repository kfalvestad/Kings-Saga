package com.kingssaga.game.view;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.enemies.Enemy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The RenderOrder class is responsible for managing the rendering order of game objects.
 * It determines the order in which objects should be rendered based on their Z-coordinate.
 */
public class RenderOrder {

    private final List<Renderable> renderables;
    private final List<TiledMapTileLayer> backgroundLayers;

    /**
     * Constructs a RenderOrder object with the specified TiledMap, player, and list of enemies.
     * @param tiledMap The TiledMap object representing the game map.
     * @param player The player Actor object.
     * @param enemies The list of enemy Actor objects.
     */
    public RenderOrder(TiledMap tiledMap, Actor player, List<Enemy> enemies) {
        renderables = new ArrayList<>();
        backgroundLayers = new ArrayList<>();

        loadNewMap(tiledMap, player, enemies);
    }

    /**
     * Loads a new map and updates the render order based on the new map's layers and objects.
     * @param tiledMap The TiledMap object representing the new game map.
     * @param player The player Actor object.
     * @param enemies The list of enemy Actor objects.
     */
    public void loadNewMap(TiledMap tiledMap, Actor player, List<Enemy> enemies) {
        renderables.clear();
        backgroundLayers.clear();

        MapLayers background = ((MapGroupLayer) tiledMap.getLayers().get("background")).getLayers();

        for (MapLayer layer : background) {
            backgroundLayers.add((TiledMapTileLayer ) layer);
        }

        MapLayers foregroundLayers = ((MapGroupLayer) tiledMap.getLayers().get("foreground")).getLayers();

        for (MapLayer layer : foregroundLayers) {
            renderables.add(new TiledMapLayerWrapper((TiledMapTileLayer) layer));
        }

        renderables.add(player);
        renderables.addAll(enemies);
    }

    /**
     * Returns the list of objects to be rendered, sorted in the correct rendering order.
     * @return The list of Renderable objects in the correct rendering order.
     */
    public List<Renderable> getRenderOrder() {
        renderables.sort(Comparator.comparing(Renderable::getZ));
        return renderables;
    }

    /**
     * Returns the list of background layers in the current map.
     * @return The list of TiledMapTileLayer objects representing the background layers.
     */
    public List<TiledMapTileLayer> getBackgroundLayers() {
        return backgroundLayers;
    }

    /**
     * Removes the specified object from the render order.
     * @param obj The Renderable object to be removed.
     */
    public void remove(Renderable obj) {
        renderables.remove(obj);
    }

    public void add(Actor actor) {
        renderables.add(actor);
    }
}
