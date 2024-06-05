package com.kingssaga.game.model;

import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.factories.Box2DFactory;

public class TestActor extends Actor {

    public TestActor() {
        this.maxHealth = 100;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    protected void updateAttackDelay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAttackDelay'");
    }

    @Override
    protected void updateSpritePosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpritePosition'");
    }

    @Override
    protected void defineBody(Box2DFactory b2df, Vector2 position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'defineBody'");
    }
    
}
