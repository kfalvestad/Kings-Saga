package com.kingssaga.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * This class contains constants used in the game.
 */
public final class Constants {
    /**
     * The maximum health value for a character allowed in the game.
     */
    public static final int MAX_HEALTH = 1000;


    /**
     * The maximum speed value for a character allowed in the game.
     */
    public static final int MAX_SPEED = 100;

    /**
     * The pixels per meter ratio used in Box2D physics simulation.
     */
    public static final float PPM = 100.0f;

    /**
     * The virtual width of the game screen.
     */
    public static final int V_WIDTH = 1024;

    /**
     * The virtual height of the game screen.
     */
    public static final int V_HEIGHT = 768;

    /**
     * The size of an item in pixels.
     */
    public static final float ITEM_SIZE = 32;

    /**
     * The size of the inventory.
     */
    public static final int INVENTORY_SIZE = 16;

    /**
     * The spawn position of the player character.
     */
    public static final Vector2 PLAYER_SPAWN =  new Vector2(188 / Constants.PPM, (Constants.V_HEIGHT - 310) / Constants.PPM);

    /** Controllers */

    /**
     * The key code for moving the character up.
     */
    public static final int MOVE_UP = Input.Keys.W;

    /**
     * The key code for moving the character down.
     */
    public static final int MOVE_DOWN = Input.Keys.S;

    /**
     * The key code for moving the character right.
     */
    public static final int MOVE_RIGHT = Input.Keys.D;

    /**
     * The key code for moving the character left.
     */
    public static final int MOVE_LEFT = Input.Keys.A;

    /**
     * The key code for attacking.
     */
    public static final int ATTACK = Input.Keys.E;

    /** Box2D Filter Bits */

    /**
     * The bit mask for the player character.
     */
    public static final short PLAYER_BIT = 2;

    /**
     * The bit mask for the hitbox of the player character.
     */
    public static final short PLAYER_HITBOX_BIT = 4;

    /**
     * The bit mask for enemy characters.
     */
    public static final short ENEMY_BIT = 8;

    /**
     * The bit mask for the hitbox of enemy characters.
     */
    public static final short ENEMY_HITBOX_BIT = 16;

    /**
     * The bit mask for weapons.
     */
    public static final short ATTACK_BIT = 32;

    /**
     * The bit mask for objects.
     */
    public static final short OBJECT_BIT = 64;

    /**
     * The bit mask for exits.
     */
    public static final short EXIT_BIT = 128;
    public static final short NPC_BIT = 256;

    /**
     * The bit mask for items.
     */
    public static final short ITEM_BIT = 512;

    public static final short DOOR_BIT = 1024;

    /** Stats */
    public static final int PLAYER_HEALTH = 20;
    public static final int PLAYER_MAX_HEALTH = 100;
    public static final float PLAYER_SPEED = 2.0f;
    public static final String PLAYER_SPRITE_PATH = "viking.png";


    public static final int BOSS_HEALTH = 150;
    public static final int BOSS_MAX_HEALTH = 150;
    public static final int BOSS_SPEED = 1;
    public static final int BOSS_ACCELERATION = 2;
    public static final String BOSS_SPRITE_PATH = "Boss.png";

    public static final int SIMPLE_ENEMY_HEALTH = 30;
    public static final int SIMPLE_ENEMY_MAX_HEALTH = 30;
    public static final int SIMPLE_ENEMY_SPEED = 10;
    public static final int SIMPLE_ENEMY_ACCELERATION = 10;
    public static final String SIMPLE_ENEMY_SPRITE_PATH = "simpleEnemy.png";

    public static final int RUNNING_BOOTS_SPEED = 2;
}
