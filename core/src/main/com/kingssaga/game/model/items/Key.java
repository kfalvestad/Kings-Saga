package com.kingssaga.game.model.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.factories.FactoryManager;

public class Key extends Item {
    private final int ID;

    public Key(FactoryManager fm, int value, Sprite sprite, int ID) {
        super(fm, "Key", value, sprite);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
