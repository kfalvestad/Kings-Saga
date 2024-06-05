package com.kingssaga.game.model.items.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.*;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.attacks.Attack;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.*;

public abstract class Weapon extends Item {
    protected int damage;
    protected int delay;
    protected final Vector2 range;

    protected Attack primaryAttack;
    protected Attack secondaryAttack;
    protected final SoundPlayer sp;

    /**
     * Constructs a new Weapon object with the specified parameters.
     *
     * @param fm   the game manager
     * @param damage    the damage value of the weapon
     * @param delay     the delay value of the weapon
     * @param name      the name of the weapon
     * @param value     the value of the weapon
     */
    public Weapon(FactoryManager fm, String name, Vector2 range, int damage, int delay, int value, Sprite sprite, SoundPlayer sp) {
        super(fm, name, value, sprite);
        this.range = range;
        this.damage = damage;
        this.delay = delay;
        this.sprite = sprite;
        this.sp = sp;
    }

    /**
     * Returns the damage value of the weapon.
     *
     * @return the damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage value of the weapon.
     *
     * @param damage the new damage value
     * @throws IllegalArgumentException if the damage value is negative
     */
    public void setDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        } else {
            this.damage = damage;
        }
    }

    /**
     * Returns the delay value of the weapon.
     *
     * @return the delay value
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Sets the delay value of the weapon.
     *
     * @param delay the new delay value
     * @throws IllegalArgumentException if the delay value is negative
     */
    public void setDelay(int delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        } else {
            this.delay = delay;
        }
    }


    /**
     * Gets the range of the melee weapon.
     *
     * @return a Vector2 representing the width and height of the attack range
     */
    public Vector2 getRange() {
        return this.range;
    }

    public void setPrimaryAttack(Attack attack) {
        this.primaryAttack = attack;
    }

    public void setSecondaryAttack(Attack attack) {
        this.secondaryAttack = attack;
    }

    public abstract void doPrimaryAttack(Vector2 position);
    public abstract void doSecondaryAttack(Vector2 position);

    public void flipAttacks() {
        if (primaryAttack != null) {
            primaryAttack.flipSprite();
        }
        if (secondaryAttack != null) {
            secondaryAttack.flipSprite();
        }
    }

    public short getMaskBits() {
        return owner instanceof Player ? Constants.ENEMY_HITBOX_BIT : Constants.PLAYER_HITBOX_BIT;
    }

    public void drawAttack(SpriteBatch batch) {
        if (primaryAttack != null && primaryAttack.isActive()) {
                primaryAttack.draw(batch);
            }

        if (secondaryAttack != null && secondaryAttack.isActive()) {
                secondaryAttack.draw(batch);
            }
        }

    public boolean isAttacking() {
        boolean primary = false;
        boolean secondary = false;

        if (primaryAttack != null) {
            primary = primaryAttack.isActive();
        }

        if (secondaryAttack != null) {
            secondary = secondaryAttack.isActive();
        }

        return primary || secondary;

    }

    public Attack getPrimaryAttack() {
        return primaryAttack;
    }

    public Attack getSecondaryAttack() {
        return secondaryAttack;
    }

    public void playSound(String file) {
        sp.playSound(file);
    }
}
