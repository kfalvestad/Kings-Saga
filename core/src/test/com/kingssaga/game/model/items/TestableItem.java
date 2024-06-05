package com.kingssaga.game.model.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kingssaga.game.model.factories.FactoryManager;

public class TestableItem extends Item {

    public TestableItem(FactoryManager fm, String name, int value, Sprite sprite) {
        super(fm, name, value, sprite);
    }
    
}
