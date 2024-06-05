package com.kingssaga.game.model.items.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.actors.Actor;


public class MeleeWeapon extends Weapon {
    private static final int value = 10;

    public MeleeWeapon(FactoryManager fm, Actor actor, String name, Vector2 range, int damage, int delay, Sprite sprite, SoundPlayer sp) {
        super(fm, name, range, damage, delay, value, sprite, sp);
        setOwner(actor);
    }

    public MeleeWeapon(FactoryManager fm, String name, Vector2 range, int damage, int delay, Sprite sprite, SoundPlayer sp) {
        super(fm, name, range, damage, delay, value, sprite, sp);
    }


    @Override
    public void doPrimaryAttack(Vector2 position) {
        if (primaryAttack != null) {
            primaryAttack.performAttack(position);
            sp.playSound(primaryAttack.getSound());
        }
    }

    @Override
    public void doSecondaryAttack(Vector2 position) {
        if (secondaryAttack != null) {
            secondaryAttack.performAttack(position);
            sp.playSound(secondaryAttack.getSound());
        }
    }
}
