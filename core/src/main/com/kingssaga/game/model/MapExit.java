package com.kingssaga.game.model;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.Box2DFactory;

public class MapExit {

    private String nextMapName;
    private Vector2 nextPos;
    
    protected Rectangle bounds;
    protected Body body;
    protected MapObject object;
    protected Fixture fixture;

    /**
     * Constructs a MapExit object.
     * Used to create an exit from one map to another.
     * 
     * @param object the map object representing the exit
     */
    public MapExit(Box2DFactory b2df, MapObject object) {
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.nextMapName = ((String) object.getProperties().get("connectToMap"));
        
        Vector2 nextMapDimensions = getNextMapDimensionsInPixels(nextMapName);

        Float nextX = (Float) object.getProperties().get("x");
        Float nextY = (nextMapDimensions.y - (Float) object.getProperties().get("y"));
        this.nextPos = new Vector2(nextX / Constants.PPM, nextY / Constants.PPM);

        BodyDef bdef = b2df.getNewBodyDef();
        FixtureDef fdef = b2df.getNewFixtureDef();

        fdef.filter.categoryBits = Constants.EXIT_BIT;
        fdef.filter.maskBits = Constants.PLAYER_BIT | Constants.ENEMY_BIT;

        PolygonShape shape = b2df.getNewPolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM, (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);

        body = b2df.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Constants.PPM, bounds.getHeight() / 2 / Constants.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        shape.dispose();
    }

    /**
     * Gets the name of the next map.
     * 
     * @return the name of the next map
     */
    public String getNextMap() {
        return nextMapName;
    }

    public void setNextMap(String string) {
        this.nextMapName = string;
    }

    /**
     * Gets the position of the next map.
     * 
     * @return the position of the next map
     */
    public Vector2 getNextPosition() {
        return this.nextPos;
    }

    public void setNextPosition(Vector2 pos) {
        this.nextPos = pos;
    }
    
    /**
     * This method returns the dimensions of the next map in pixels.
     * Tiled uses an inverted y-axis, but libgdx uses a normal y-axis.
     * This method corrects for that.
     * 
     * @param mapName the name of the next map
     * @return the dimensions of the next map in pixels
     */
    private Vector2 getNextMapDimensionsInPixels(String mapName) {
        TiledMap map = new TmxMapLoader().load(mapName);
        MapLayers layers = ((MapGroupLayer) map.getLayers().get("background")).getLayers();
        float mapHeightInTiles = ((TiledMapTileLayer) layers.get(0)).getHeight();
        float mapHeightInPixels = mapHeightInTiles * ((TiledMapTileLayer) layers.get(0)).getTileHeight();
        float mapWidthInTiles = ((TiledMapTileLayer) layers.get(0)).getWidth();
        float mapWidthInPixels = mapWidthInTiles * ((TiledMapTileLayer) layers.get(0)).getTileWidth();
        map.dispose();
        return new Vector2(mapWidthInPixels, mapHeightInPixels);
    }
}
