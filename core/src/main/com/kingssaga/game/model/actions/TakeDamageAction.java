package com.kingssaga.game.model.actions;

import com.kingssaga.game.model.actors.Actor;

/**
 * The TakeDamageAction class represents an action that causes damage to a target Actor.
 */
public class TakeDamageAction implements Action {

    private final int damage;
    private final Actor target;

    /**
     * Constructs a TakeDamageAction object with the specified attack value and target Actor.
     *
     * @param damage the amount of attack damage to be inflicted
     * @param target the target Actor to receive the damage
     */
    public TakeDamageAction(int damage, Actor target) {
        if (damage <= 0) {
            throw new IllegalArgumentException("Damage must not be zero or negative");
        }
        this.damage = damage;
        this.target = target;
    }

    /**
     * Executes the TakeDamageAction by calculating the new health of the target Actor
     * and updating its health value accordingly.
     *
     * @return the result of the action execution (ActionResult.SUCCESS)
     */
    @Override
    public ActionResult execute() {
        int newHealth = calculateHealth(target);
        target.setHealth(newHealth);
        return ActionResult.SUCCESS;
    }

    /**
     * Calculates the new health value of the target Actor after taking damage.
     *
     * @param target the target Actor to calculate the new health for
     * @return the calculated new health value
     */
    private int calculateHealth(Actor target) {
        int health = target.getHealth() - damage;
        return Math.max(health, 0);
    }

}
