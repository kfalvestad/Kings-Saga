package com.kingssaga.game.model.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.Constants;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.attacks.ChargeAttack;
import com.kingssaga.game.model.attacks.SlamAttack;
import com.kingssaga.game.model.attacks.SwingAttack;
import com.kingssaga.game.model.items.weapons.Weapon;

public class AttackFactory {

    private final FactoryManager fm;

    public AttackFactory(FactoryManager fm) {
        this.fm = fm;
    }

    public ChargeAttack createChargeAttack(Weapon weapon, Box2DLocation target) {
        Sprite sprite = createSprite("slash.png");
        ChargeAttack chargeAttack = new ChargeAttack(weapon, fm.getBox2DFactory(), sprite, target);
        return chargeAttack;
    }

    public SwingAttack createSwingAttack(Weapon weapon) {
        Sprite sprite = createSprite("slash.png");
        SwingAttack swingAttack = new SwingAttack(weapon, fm.getBox2DFactory(), sprite);
        return swingAttack;
    }

    public SlamAttack createSlamAttack(Weapon weapon) {
        Sprite sprite = createSprite("groundslam.png");
        SlamAttack slamAttack = new SlamAttack(weapon, fm.getBox2DFactory(), sprite);
        return slamAttack;
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
