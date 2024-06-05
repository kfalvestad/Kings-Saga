package com.kingssaga.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.kingssaga.game.Constants;

import com.badlogic.gdx.math.Rectangle;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.factories.GraphicsFactory;

public class Door implements Disposable {

    private boolean locked;
    private final int ID;
    private Body body;
    private final Box2DFactory b2df;
    private final BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private final PolygonShape shape;
    private final Rectangle rect;
    private final Sprite sprite;
    private final SoundPlayer sp;

    public Door(Box2DFactory b2df, GraphicsFactory gf, SoundPlayer sp, Rectangle rect, int ID) {
            this.b2df = b2df;
            this.ID = ID;
            this.rect = rect;
            this.sp = sp;
            this.sprite = gf.getNewSprite(gf.getNewTexture("door.png"));
            locked = true;
            bodyDef = b2df.getNewBodyDef();
            fixtureDef = b2df.getNewFixtureDef();
            shape = b2df.getNewPolygonShape();
            createBody();
            float width = sprite.getWidth() / Constants.PPM;
            float height = sprite.getHeight() / Constants.PPM;
            sprite.setBounds(0, 0, width, height);
            sprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y);
        }

    public boolean isLocked() {
            return locked;
        }

    public void unlock() {
            locked = false;
            b2df.removeBodyAfterDelay(body, 0);
            sp.playSound("unlock.mp3");
        }


        public int getID() {
         return this.ID;
        }

        public void createBody() {
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = b2df.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);

            fixtureDef = b2df.getNewFixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Constants.DOOR_BIT;
            body.createFixture(fixtureDef).setUserData(this);
        }

    @Override
    public void dispose() {
        shape.dispose();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
