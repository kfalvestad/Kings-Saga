package com.kingssaga.game.model.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;
import com.kingssaga.game.model.BodyProvider;
import com.kingssaga.game.model.actions.TakeDamageAction;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.weapons.Weapon;
import com.kingssaga.game.view.Renderable;

import java.util.List;

/**
 * The {@code IActor} interface represents an actor in the game.
 * An actor is any entity that can perform actions, such as attacking, taking
 * damage, and updating its state.
 * This interface extends the {@code Renderable} and {@code BodyProvider}
 * interfaces.
 */
public interface IActor extends Renderable, BodyProvider, Disposable {
    /**
     * Updates the state of the actor.
     */
    void update();

    /**
     * This method is called when the actor is attacked.
     * This method does not update the health of the actor,
     * but is used to receive information about the attack.
     * 
     * @param damageAction The damage action received from the attack.
     */
    void takeDamage(TakeDamageAction damageAction);


    /**
     * Checks if the actor is within the attack range of the specified target.
     * 
     * @param target      The target location.
     * @param attackRange The attack range of the actor.
     * @return {@code true} if the actor is within the attack range of the target,
     *         {@code false} otherwise.
     */
    boolean isWithinRange(Vector2 target, Vector2 attackRange);

    /**
     * Gets the current health of the actor.
     * 
     * @return The current health of the actor.
     */
    int getHealth();

    void setHealth(int health);

    /**
     * Restores the health of the actor by the specified amount.
     * If the health restored exceeds the maximum health, the health is set to the
     * maximum health.
     * 
     * @param healthRestored The amount of health to restore.
     */
    void restoreHealth(int healthRestored);

    /**
     * Checks if the actor is alive.
     * 
     * @return {@code true} if the actor is alive, {@code false} otherwise.
     */
    boolean isAlive();

    /**
     * Gets the speed of the actor.
     * 
     * @return The speed of the actor.
     */
    float getSpeed();

    /**
     * Sets the speed of the actor.
     * 
     * @param speed The speed value to set.
     */
    void setSpeed(int speed);

    /**
     * Gets the position of the actor.
     * 
     * @return The position of the actor.
     */
    Vector2 getPosition();

    int getAttackDelay();


    /**
     * Sets the position of the actor.
     * 
     * @param position The position to set.
     */
    void setPosition(Vector2 position);

    /**
     * Gets the body of the actor.
     * 
     * @return The body of the actor.
     */
    Body getBody();

    /**
     * Gets the width of the actor.
     * 
     * @return The width of the actor.
     */
    float getWidth();

    /**
     * Gets the height of the actor.
     * 
     * @return The height of the actor.
     */
    float getHeight();

    BodyDef.BodyType getBodyType();

    /**
     * Sets the wielded weapon of the actor.
     * 
     * @param weapon The weapon to set as the wielded weapon.
     */
    void setWieldedWeapon(Weapon weapon);

    /**
     * Draws the actor on the screen.
     *
     * @param batch The sprite batch to draw the actor with.
     */
    void draw(SpriteBatch batch);

    void setVelocity(Vector2 direction, int scale);

    List<Item> getInventory();
}
