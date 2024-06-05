package com.kingssaga.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kingssaga.game.Constants;
import com.kingssaga.game.GameState;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.controller.ControllableModel;
import com.kingssaga.game.model.actors.NPC;
import com.kingssaga.game.model.actors.Stats;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.enemies.*;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.factories.*;
import com.kingssaga.game.model.items.*;
import com.kingssaga.game.view.*;
import com.kingssaga.game.view.screens.GameScreen;

import java.util.*;

/**
 * The GameManager class manages the game logic and state. It handles updating the game world, handling player interactions,
 * managing enemies and items, and rendering the game objects. It also manages the physics simulation using Box2D.
 */
public class GameManager implements Disposable, FactoryManager, ControllableModel, ViewableModel {
    private final GameScreen screen;

    protected TiledMap tiledMap;
    private TmxMapLoader tmxMapLoader;

    protected Player player;

    private Boolean loadObjectsNextUpdate;
    private Vector2 nextPos;

    private B2DWorldCreator creator;

    protected RenderOrder ro;

    private GraphicsFactory gFactory;
    private Box2DFactory b2dFactory;
    private LootFactory lootFactory;
    private WeaponFactory weaponFactory;
    private ActorFactory actorFactory;
    private GameObjectFactory gameObjectFactory;
    private ItemFactory itemFactory;
    private AttackFactory attackFactory;

    private final List<Item> itemsOnGround;

    private final List<Door> doors;

    public GameManager(GameScreen screen) {
        this.screen = screen;
        screen.setState(GameState.ACTIVE);

        factoryInit();
        b2dFactory.getContactListener().setManager(this);

        this.itemsOnGround = new ArrayList<>();
        this.doors = new ArrayList<>();
        loadObjectsNextUpdate = false;


        setUp();

    }

    protected void setUp() {
        gFactory = new GraphicsFactory();
        tmxMapLoader = new TmxMapLoader();
        tiledMap = tmxMapLoader.load("maps/map01.tmx");

        creator = new B2DWorldCreator(this);
        creator.loadObjects(tiledMap);

        player = actorFactory.createPlayer(this, screen);

        ro = new RenderOrder(tiledMap, player, actorFactory.getEnemies());

        actorFactory.setRenderOrder(ro);
    }

    /**
     * Initializes the factories used by the GameManager.
     */
    private void factoryInit() {
        b2dFactory = new Box2DFactory();
        weaponFactory = new MeleeWeaponFactory(this, screen);
        lootFactory = new RandomLootFactory(this);
        actorFactory = new ActorFactory(this, screen);
        itemFactory = new ItemFactory(this);
        gameObjectFactory = new GameObjectFactory(this);
        attackFactory = new AttackFactory(this);
    }

    private void loadObjects() {
        creator.loadObjects(tiledMap);
        player.setPosition(nextPos);
        ro.loadNewMap(tiledMap, player, actorFactory.getEnemies());
        loadObjectsNextUpdate = false;
    }

    private void removeBodies() {
        Map<Body, Integer> bodies = b2dFactory.getBodiesToBeDestroyed();
        Iterator<Map.Entry<Body, Integer>> iterator = bodies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Body, Integer> entry = iterator.next();
            if (entry.getValue() <= 0) {
                b2dFactory.destroyBody(entry.getKey());
                iterator.remove();
            } else {
                bodies.put(entry.getKey(), entry.getValue() - 1);
            }
        }
    }


    public void update() {
        b2dFactory.step();

        removeBodies();

        if (loadObjectsNextUpdate) {
            loadObjects();
        }

        if (!player.isAlive()) {
            screen.setState(GameState.GAME_OVER);
        } else {
            player.update();
        }

        if (itemsOnGround != null) {
            for (Item item : itemsOnGround) {
                item.updateSpritePosition();
            }
        }

        updateEnemies();
    }

    protected void updateEnemies() {
        Iterator<Enemy> enemiesIterator = actorFactory.getEnemies().iterator();
        while (enemiesIterator.hasNext()) {
            Enemy enemy = enemiesIterator.next();
            if (enemy.isAlive()) {
                enemy.update();
            } else {
                if (enemy instanceof Boss) {
                    screen.setState(GameState.VICTORY);
                } else {
                    b2dFactory.getBodiesToBeDestroyed().put(enemy.getBody(), 0);
                    enemiesIterator.remove();
                    ro.remove(enemy);
                    enemy.die(itemsOnGround);
                }
            }
        }

    }

    /**
     * Handles the player's contact with other objects in the game world.
     * If the player contacts an item, the item will be added to the player's inventory.
     * If the player contacts a map exit, the game will load the next map.
     */
    public void handlePlayerContact(Object obj) {
        if (obj instanceof Item) {
            boolean success = ((Item) obj).handleInteraction(player);
            if (success) {
                b2dFactory.removeBodyAfterDelay(((Item) obj).getBody(), 0);
                itemsOnGround.remove(obj);
            }
        } else if (obj instanceof MapExit) {
            destroyAllBodies();
            loadNewMap(((MapExit) obj).getNextMap());
            loadObjectsNextUpdate = true;
            nextPos = ((MapExit) obj).getNextPosition();
            actorFactory.getEnemies().clear();
            itemsOnGround.clear();
        } else if (obj instanceof NPC) {
            screen.showDialogue(((NPC) obj).getDialogue(), ((NPC) obj).getName());
        } else if (obj instanceof Door) {
            int ID = ((Door) obj).getID();
            if (player.hasKey(ID)) {
                ((Door) obj).unlock();
            }
        }
    }

    public void handleEndNPCContact() {
        screen.hideDialogue();
    }


    /**
     * Loads a new map and updates the game screen with the new map.
     */
    private void loadNewMap(String mapName) {
        tiledMap.dispose();
        tiledMap = tmxMapLoader.load(mapName);
        screen.loadNewMap(tiledMap);
    }

    /**
     * Destroys all bodies in the game world except for the player's body.
     * This method is used when the player moves to a new map.
     */
    private void destroyAllBodies() {
        Array<Body> bodies = new Array<>();
        b2dFactory.getBodies(bodies);
        for (Body body : bodies) {
            if (!body.equals(player.getBody())) {
                b2dFactory.removeBodyAfterDelay(body, 0);
            }
        }
    }

    public TiledMap getCurrentMap() {
        return this.tiledMap;
    }

    /**
     * This method calculates the new camera position based on the player's position.
     * The camera will follow the player, but will not go outside the map.
     * If the player is close to the edge of the map, the camera will stop moving.
     */
    public void calculateNewCameraPosition(OrthographicCamera camera, Viewport viewport) {
        float newCameraX = calculateNewCameraX(viewport);
        float newCameraY = calculateNewCameraY(viewport);
        camera.position.set(newCameraX, newCameraY, 0);
    }

    private float calculateNewCameraX(Viewport viewport) {
        float playerX = player.getPosition().x;
        float viewWidth = viewport.getWorldWidth();
        float mapWidth = calculateMapWidth();
        if (playerX > (mapWidth - viewWidth / 2)) {
            return mapWidth - viewWidth / 2;
        } else {
            return Math.max(playerX, viewWidth / 2);
        }
    }

    private float calculateNewCameraY(Viewport viewport) {
        float playerY = player.getPosition().y;
        float viewHeight = viewport.getWorldHeight();
        float mapHeight = calculateMapHeight();
        if (playerY > (mapHeight - viewHeight / 2)) {
            return mapHeight - viewHeight / 2;
        } else {
            return Math.max(playerY, viewHeight / 2);
        }
    }

    private float calculateMapWidth() {
        Integer tileCountWidth = tiledMap.getProperties().get("width", Integer.class);
        Integer tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        return tileCountWidth * tileWidth / Constants.PPM;
    }

    private float calculateMapHeight() {
        Integer tileCountHeight = tiledMap.getProperties().get("height", Integer.class);
        Integer tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
        return tileCountHeight * tileHeight / Constants.PPM;
    }

    /**
     * Draws the game objects.
     */
    public void draw(SpriteBatch batch, OrthogonalTiledMapRenderer renderer) {

        for (TiledMapTileLayer layer : ro.getBackgroundLayers()) {
            renderer.renderTileLayer(layer);
        }

        if (tiledMap.getProperties().containsKey("doorID")) {
            for (Door door : doors) {
                if (door.isLocked() && tiledMap.getProperties().get("doorID", Integer.class) == door.getID()) {
                    door.draw(batch);
                }
            }
        }

        for (Renderable r : ro.getRenderOrder()) {
            if (r instanceof TiledMapLayerWrapper) {
                renderer.renderTileLayer(((TiledMapLayerWrapper) r).getTileLayer());
            } else if (r instanceof Actor) {
                ((Actor) r).draw(batch);
            }
        }

        for (Item item : itemsOnGround) {
            item.draw(batch);
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        b2dFactory.dispose();
        player.dispose();

        for (Actor enemy : actorFactory.getEnemies()) {
            enemy.dispose();
        }
    }

    @Override
    public GraphicsFactory getGraphicsFactory() {
        return this.gFactory;
    }

    @Override
    public Box2DFactory getBox2DFactory() {
        return this.b2dFactory;
    }

    @Override
    public WeaponFactory getWeaponFactory() {
        return this.weaponFactory;
    }

    @Override
    public LootFactory getLootFactory() {
        return this.lootFactory;
    }

    @Override
    public ActorFactory getActorFactory() {
        return this.actorFactory;
    }

    @Override
    public ItemFactory getItemFactory() {
        return this.itemFactory;
    }

    @Override
    public GameObjectFactory getGameObjectFactory() {
        return this.gameObjectFactory;
    }

    @Override
    public AttackFactory getAttackFactory() {
        return this.attackFactory;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void addDoor(Door door) {
        doors.add(door);
        giveKey(door.getID());
    }

    public void giveKey(int ID) {
        int randomEnemy = new Random().nextInt(actorFactory.getEnemies().size());
        Enemy enemy = actorFactory.getEnemies().get(randomEnemy);
        Key key = itemFactory.getNewKey(ID);
        key.setOwner(enemy);
        key.setValue(100);
        enemy.addToLoot(key);
    }

    public SoundPlayer getSoundPlayer() {
        return this.screen;
    }

    @Override
    public void movePlayer(boolean right, boolean left, boolean up, boolean down) {
        player.move(right, left, up, down);
    }

    @Override
    public void playerAttack() {
        player.attack();
    }


    @Override
    public Stats getPlayerStats() {
        return this.player;
    }
}
