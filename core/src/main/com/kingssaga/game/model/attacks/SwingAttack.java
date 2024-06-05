package com.kingssaga.game.model.attacks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.weapons.Weapon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwingAttack extends Attack{
    
    private final List<String> sounds;

    public SwingAttack(Weapon weapon, Box2DFactory b2df, Sprite sprite) {
        super(weapon, b2df, sprite);
        damageModifier = 1;
        sounds = new ArrayList<>();
        sounds.add("swing1.mp3");
        sounds.add("swing2.mp3");
        sounds.add("swing3.mp3");
        defineBody();
    }

    @Override
    public void performAttack(Vector2 position) {
        isActive = true;
        float x = getBodyX(position.x);
        float spritePositionX = getSpriteX(position.x);
        Vector2 range = weapon.getRange();

        bodyDef.position.set(x, position.y);
        Body body = b2df.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);

        sprite.setBounds(0, 0, range.x, range.y * 2);
        sprite.setPosition(spritePositionX, body.getPosition().y - range.y);
        b2df.removeBodyAfterDelay(body, 1);
    }

    @Override
    public String getSound() {
        int randInt = new Random().nextInt(sounds.size());
        return sounds.get(randInt);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        isActive = false;
    }

    private float getBodyX(float posX) {
        float width = weapon.getRange().x + weapon.getOwner().getWidth() / 2;
        return posX + (isFlipped ? -width : width);
    }

    private float getSpriteX(float posX) {
        return isFlipped ? posX - weapon.getOwner().getWidth() - 0.1f: posX + weapon.getOwner().getWidth() / 3;
    }
}
