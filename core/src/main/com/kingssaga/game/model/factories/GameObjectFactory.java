package com.kingssaga.game.model.factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.MapExit;
import com.kingssaga.game.model.Door;
import com.kingssaga.game.model.Obstacle;

public class GameObjectFactory {

    private final GameManager manager;

    public GameObjectFactory(GameManager manager) {
        this.manager = manager;
    }

    public void createMapExit(MapObject object) {
        new MapExit(manager.getBox2DFactory(), object);
    }

    public void createObstacle(Rectangle rect) {
        new Obstacle(manager.getBox2DFactory(), rect);
    }

    public void createDoor(int ID, RectangleMapObject object) {
        boolean willCreate = true;
        for (Door door : manager.getDoors()) {
            if (door.getID() == ID) {
                if (door.isLocked()) {
                    door.createBody();
                    manager.giveKey(ID);
                }
                willCreate = false;
                break;
            }
        }
        if (willCreate) {
            manager.addDoor(new Door(manager.getBox2DFactory(), manager.getGraphicsFactory(), manager.getSoundPlayer(), object.getRectangle(), ID));
        }
    }
}
