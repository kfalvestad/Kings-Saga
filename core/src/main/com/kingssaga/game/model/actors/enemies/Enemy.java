package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import java.util.List;
import java.util.ArrayList;

import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.controller.ai.Box2DProximity;
import com.kingssaga.game.controller.ai.Box2DSteeringEntity;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.items.Item;

/**
 * The abstract base class for all enemy actors in the game.
 */
public abstract class Enemy extends Actor {

    protected BlendedSteering<Vector2> behavior;
    protected Box2DSteeringEntity steering;
    protected Location<Vector2> playerLocation;
    protected Proximity<Vector2> proximity;
    protected CoinPouch coinPouch;
    protected Vector2 initialPosition;

    /**
     * Constructs a new Enemy with the specified attributes.
     *
     * @param health       the health points of the enemy
     * @param speed        the speed of the enemy
     * @param acceleration the acceleration of the enemy
     */
    public Enemy(int health, int speed, int acceleration, SoundPlayer sp) {
        this.health = health;
        this.speed = speed;
        this.acceleration = acceleration;
        this.inventory = new ArrayList<>();
        this.sp = sp;

        hitSounds = new ArrayList<>();
        hitSounds.add("hit1.mp3");
        hitSounds.add("hit2.mp3");
        hitSounds.add("hit3.mp3");
    }


    /**
     * Sets the location of the player.
     *
     * @param playerLocation the location of the player
     */
    public void setPlayerLocation(Location<Vector2> playerLocation) {
        this.playerLocation = playerLocation;
    }

    /**
     * Sets the behavior of the enemy to seek the player.
     */
    public void setBehavior() {
        proximity = new Box2DProximity(steering, body.getWorld(), 10);
        behavior = new BlendedSteering<>(steering);
        behavior.add(new CollisionAvoidance<>(steering, proximity), 0.3f);
        behavior.add(new Seek<>(steering, playerLocation), 0.7f);
    }

    public Steerable<Vector2> getSteering() {
        return this.steering;
    }

    /**
     * Returns the list of items in the enemy's loot.
     *
     * @return the list of items in the enemy's loot
     */
    public List<Item> getLoot() {
        return this.inventory;
    }

    /**
     * Adds a list of items to the enemy's loot.
     *
     * @param loot the list of items to add to the enemy's loot
     */
    public void addToLoot(List<Item> loot) {
        for (Item item : loot) {
            item.setOwner(this);
        }
        this.inventory.addAll(loot);
    }

    /**
     * Adds an item to the enemy's loot.
     *
     * @param item the item to add to the enemy's loot
     */
    public void addToLoot(Item item) {
        item.setOwner(this);
        this.inventory.add(item);
    }

    /**
     * Updates the direction the enemy is facing based on its velocity and a margin.
     * If the enemy is not facing left and its velocity is less than the negative margin, it flips direction.
     * If the enemy is facing left and its velocity is greater than or equal to the margin, it flips direction.
     *
     * @param margin The margin used to determine when to flip the enemy's direction.
     */
    protected void updateFacing(float margin) {
        if (!isFacingLeft && steering.getLinearVelocity().x < -margin) {
            flip();
        } else if (isFacingLeft && steering.getLinearVelocity().x >= margin) {
            flip();
        }
    }

    protected abstract void updateSteering();

    /**
     * Drops all items in the enemy's loot at the enemy's current position.
     */
    public void dropLoot() {
        for (Item item : inventory) {
            item.drop(body.getPosition());
        }
        inventory.clear();
    }

    public void die(List<Item> itemOnGround) {
        itemOnGround.addAll(inventory);
        dropLoot();
    }
}
