package com.kingssaga.game.model.factories;

import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.enemies.Boss;
import com.kingssaga.game.model.actors.enemies.SimpleEnemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.items.weapons.Weapon;

/**
 * This interface defines the methods that a WeaponFactory should implement.
 * A WeaponFactory is responsible for creating and assigning weapons to actors in the game.
 */
public interface WeaponFactory {

    /**
     * Creates a weapon with the given parameters.
     *
     * @param name   the name of the weapon
     * @param range  the range of the weapon
     * @param damage the damage of the weapon
     * @param delay  the delay of the weapon
     * @return the created weapon
     */
    public Weapon createWeapon(String name, Vector2 range, int damage, int delay);

    /**
     * Creates a weapon with the given parameters and assigns it to an actor.
     *
     * @param name   the name of the weapon
     * @param actor  the actor to whom the weapon will be assigned
     * @param range  the range of the weapon
     * @param damage the damage of the weapon
     * @param delay  the delay of the weapon
     * @return the created weapon
     */
    public Weapon createWeapon(String name, Actor actor, Vector2 range, int damage, int delay);

    /**
     * Assigns a spawn weapon to a boss.
     *
     * @param boss the boss to whom the weapon will be assigned
     */
     void giveSpawnWeapon(Boss boss, Box2DLocation playerLocation);

    /**
     * Assigns a spawn weapon to a simple enemy.
     *
     * @param enemy the simple enemy to whom the weapon will be assigned
     */
     void giveSpawnWeapon(SimpleEnemy enemy);

    /**
     * Assigns a spawn weapon to a player.
     *
     * @param player the player to whom the weapon will be assigned
     */
     void giveSpawnWeapon(Player player);
}