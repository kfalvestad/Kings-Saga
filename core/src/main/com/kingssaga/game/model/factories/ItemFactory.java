package com.kingssaga.game.model.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.ItemType;
import com.kingssaga.game.model.items.Key;
import com.kingssaga.game.model.items.potions.HealthPotion;
import com.kingssaga.game.model.items.potions.SpeedPotion;
import com.kingssaga.game.model.items.weapons.MeleeWeapon;

public class ItemFactory {

    private final GameManager manager;

    public ItemFactory(GameManager manager) {
        this.manager = manager;
    }

    /**
     * Creates a new item of the specified type.
     *
     * @param itemType the type of item to create
     * @return the new item
     * @throws IllegalArgumentException if the item type is invalid
     */
    public Item getNewItem(ItemType itemType) {
        switch (itemType) {
            case COIN_POUCH:
                CoinPouch coinPouch = getNewCoinPouch();
                coinPouch.fillWithRandomAmount(1, 100);
                return coinPouch;
            case MELEE_WEAPON:
                MeleeWeapon meleeWeapon = (MeleeWeapon)manager.getWeaponFactory().createWeapon("Sword", new Vector2(0.5f, 0.5f), 10, 1);
                return meleeWeapon;
            case HEALTH_POTION:
                return getNewHealthPotion();
            case SPEED_POTION:
                return getNewSpeedPotion();
            default:
                throw new IllegalArgumentException("Invalid item type");
        }
    }

    public CoinPouch getNewCoinPouch() {
        Sprite sprite = createSprite("coin_pouch.png");
        return new CoinPouch(manager, sprite);
    }

    public Key getNewKey(int iD) {
        Sprite sprite = createSprite("key.png");
        return new Key(manager, 10, sprite, iD);
    }

    public HealthPotion getNewHealthPotion() {
        Sprite sprite = createSprite("health_potion.png");
        return new HealthPotion(manager, 10, sprite);
    }

    public SpeedPotion getNewSpeedPotion() {
        Sprite sprite = createSprite("speed_potion.png");
        return new SpeedPotion(manager, 1.3f, 10.0f, sprite);
    }

    private Sprite createSprite(String imagePath) {
        Texture texture = manager.getGraphicsFactory().getNewTexture(imagePath);
        Sprite sprite = manager.getGraphicsFactory().getNewSprite(texture);
        setCorrectSpriteSize(sprite);
        return sprite;
    }

    private void setCorrectSpriteSize(Sprite sprite) {
        sprite.setSize(Constants.ITEM_SIZE / Constants.PPM, Constants.ITEM_SIZE / Constants.PPM);
    }


}
