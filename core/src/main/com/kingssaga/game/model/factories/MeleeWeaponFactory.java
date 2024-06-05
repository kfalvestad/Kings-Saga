package com.kingssaga.game.model.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.enemies.Boss;
import com.kingssaga.game.model.actors.enemies.SimpleEnemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.attacks.ChargeAttack;
import com.kingssaga.game.model.attacks.SlamAttack;
import com.kingssaga.game.model.attacks.SwingAttack;
import com.kingssaga.game.model.items.weapons.MeleeWeapon;
import com.kingssaga.game.model.items.weapons.Weapon;

public class MeleeWeaponFactory implements WeaponFactory {

    final FactoryManager fm;
    final SoundPlayer sp;
    final String imagePath = "melee_weapon.png";
    Sprite sprite;

    public MeleeWeaponFactory(FactoryManager fm, SoundPlayer sp) {
        this.fm = fm;
        this.sp = sp;
    }

    @Override
    public Weapon createWeapon(String name, Actor actor, Vector2 range, int damage, int delay) {
        sprite = createSprite(imagePath);
        return new MeleeWeapon(fm, actor, name, range, damage, delay, sprite, sp);
    }

    @Override
    public Weapon createWeapon(String name, Vector2 range, int damage, int delay) {
        sprite = createSprite(imagePath);
        return new MeleeWeapon(fm, name, range, damage, delay, sprite, sp);
    }

    @Override
    public void giveSpawnWeapon(Boss boss, Box2DLocation target) {
        Weapon weapon = createWeapon("Giant's Axe", boss, new Vector2(3, 3), 15, 50);
        AttackFactory af = fm.getAttackFactory();
        ChargeAttack chargeAttack = af.createChargeAttack(weapon, target);
        SlamAttack slamAttack = af.createSlamAttack(weapon);
        weapon.setPrimaryAttack(chargeAttack);
        weapon.setSecondaryAttack(slamAttack);
        weapon.setOwner(boss);
        weapon.flipAttacks();
        boss.setWieldedWeapon(weapon);
    }

    @Override
    public void giveSpawnWeapon(SimpleEnemy enemy) {
        Weapon weapon = createWeapon("Rusty Sword", enemy, new Vector2(enemy.getWidth(), enemy.getHeight() / 2), 5,
                150);
        AttackFactory af = fm.getAttackFactory();
        SwingAttack attack = af.createSwingAttack(weapon);
        weapon.setPrimaryAttack(attack);
        weapon.setOwner(enemy);
        enemy.setWieldedWeapon(weapon);
    }

    @Override
    public void giveSpawnWeapon(Player player) {
        Weapon weapon = createWeapon("Hero's Sword", player,
                new Vector2(player.getWidth() / 1.5f, player.getHeight() / 2), 10,
                40);
        AttackFactory af = fm.getAttackFactory();
        SwingAttack attack = af.createSwingAttack(weapon);
        weapon.setPrimaryAttack(attack);
        weapon.setOwner(player);
        player.setWieldedWeapon(weapon);
    }

    private Sprite createSprite(String imagePath) {
        Texture texture = fm.getGraphicsFactory().getNewTexture(imagePath);
        Sprite sprite = new Sprite(texture);
        setCorrectSpriteSize(sprite);
        return sprite;
    }

    private void setCorrectSpriteSize(Sprite sprite) {
        sprite.setSize(Constants.ITEM_SIZE / Constants.PPM, Constants.ITEM_SIZE / Constants.PPM);
    }
}