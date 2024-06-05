package com.kingssaga.game.model.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.kingssaga.game.*;
import com.kingssaga.game.model.BodyProvider;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.model.factories.FactoryManager;

/**
 * The base class for all items in the game.
 */
public abstract class Item implements BodyProvider {
    protected String name;
    protected int value;
    protected Actor owner;

    protected FactoryManager fm;
    protected Sprite sprite;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected Body body;
    protected String imagePath;

    /**
     * Constructs a new Item object.
     * 
     * @param fm        the game manager
     * @param name      the name of the item
     * @param value     the value of the item
     * @throws IllegalArgumentException if the name is null or the value is negative
     */
    public Item(FactoryManager fm, String name, int value, Sprite sprite) {
        if (name == null) {
            throw new IllegalArgumentException("Name of item cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Value of item cannot be negative");
        }
        this.fm = fm;
        this.name = name;
        this.value = value;
        this.sprite = sprite;
    }

    /**
     * Returns the name of the item.
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the item.
     * 
     * @return the value of the item
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the item.
     * 
     * @param value the value of the item
     * @throws IllegalArgumentException if the value is negative
     */
    public void setValue(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value of item cannot be negative");
        } else {
            this.value = value;
        }
    }

    /**
     * Returns the owner of the item.
     * 
     * @return the owner of the item
     */
    public Actor getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the item.
     * 
     * @param owner the owner of the item
     */
    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    /**
     * Drops the item at the specified position.
     * 
     * @param position the position to drop the item
     * @throws IllegalStateException if the item does not have an owner
     */
    public void drop(Vector2 position) {
        createAsPhysicalObject(position);
    }

    /**
     * Creates the item as a physical object at the specified position.
     * 
     * @param position the position to create the item
     */
    private void createAsPhysicalObject(Vector2 position) {
        BodyDef bodyDef = createBodyDef(position);
        this.body = fm.getBox2DFactory().createBody(bodyDef);

        FixtureDef fixtureDef = createFixtureDef();
        body.createFixture(fixtureDef).setUserData(this);
        body.setUserData(this);

        updateSpritePosition();

        disposeShape(fixtureDef.shape);
    }

    private BodyDef createBodyDef(Vector2 position) {
        BodyDef bodyDef = fm.getBox2DFactory().getNewBodyDef();
        bodyDef.type = getBodyType();
        bodyDef.position.set(position);
        return bodyDef;
    }

    private FixtureDef createFixtureDef() {
        FixtureDef fixtureDef = fm.getBox2DFactory().getNewFixtureDef();
        fixtureDef.filter.categoryBits = Constants.ITEM_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_HITBOX_BIT | Constants.ITEM_BIT | Constants.OBJECT_BIT;

        PolygonShape shape = fm.getBox2DFactory().getNewPolygonShape();
        shape.setAsBox(Constants.ITEM_SIZE / Constants.PPM / 2, Constants.ITEM_SIZE / Constants.PPM / 2);

        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.99f;
        fixtureDef.restitution = 0.6f;

        return fixtureDef;
    }

    /**
     * Draws the item on the specified sprite batch.
     * 
     * @param batch the sprite batch to draw on
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Disposes of the item's resources.
     */
    public void dispose() {
        sprite.getTexture().dispose();
    }

    /**
     * Returns the body of the item.
     * 
     * @return the body of the item
     */
    public Body getBody() {
        return body;
    }

    /**
     * Updates the position of the item's sprite based on its body position.
     */
    public void updateSpritePosition() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    private void disposeShape(Shape shape) {
        shape.dispose();
    }

    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.DynamicBody;
    }

    /**
     * Handles the interaction of the item with the specified player.
     *
     * @param player the player to interact with
     * @return true if the interaction was successful, false otherwise
     */
    public boolean handleInteraction(Player player) {
        player.addToInventory(this);
        return true;
    }
}
