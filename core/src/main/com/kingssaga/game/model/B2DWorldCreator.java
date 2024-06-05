package com.kingssaga.game.model;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.ActorFactory;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.factories.GameObjectFactory;

public class B2DWorldCreator {

    private final FactoryManager fm;

    /**
     * Constructs a B2DWorldCreator object.
     *
     * @param fm the FactoryManager instance
     */
    public B2DWorldCreator(FactoryManager fm) {
        this.fm = fm;
    }

    /**
     * Loads objects from the TiledMap and creates corresponding Box2D bodies.
     *
     * @param map the TiledMap containing the objects
     */
    public void loadObjects(TiledMap map) {
        ActorFactory af = fm.getActorFactory();
        GameObjectFactory of = fm.getGameObjectFactory();

        for (MapLayer layer : ((MapGroupLayer) map.getLayers().get("objects")).getLayers()) {
            if (layer.getName().equalsIgnoreCase("exits")) {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                    of.createMapExit(object);
                }
            } 
            else if (layer.getName().equalsIgnoreCase("npc")) {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                    af.createNPC(object);
                }
            }
            else if (layer.getName().equalsIgnoreCase("simpleEnemy")) {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                    af.createSimpleEnemy(new Vector2(object.getRectangle().x / Constants.PPM, object.getRectangle().y / Constants.PPM));
                }
            }
            else if (layer.getName().equalsIgnoreCase("boss")) {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                    af.createBoss(new Vector2(object.getRectangle().x / Constants.PPM, object.getRectangle().y / Constants.PPM));
                }
            }

            else if (layer.getName().equalsIgnoreCase("door")) {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                    int ID = map.getProperties().get("doorID", Integer.class);
                    of.createDoor(ID, object);
                }
            }
            else {
                for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                   of.createObstacle(object.getRectangle());
                }
            } 
        }
    }

}
