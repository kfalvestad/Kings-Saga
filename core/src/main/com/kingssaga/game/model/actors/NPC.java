package com.kingssaga.game.model.actors;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.Box2DFactory;

import java.util.ArrayList;
import java.util.Collections;

public class NPC {

    private String name;
    private String message;
    private final ArrayList<String> dialogue;
    private final Box2DFactory b2df;
    private final MapObject object;


    /**
     * Creates a new NPC object with the specified Box2DFactory and MapObject.
     * 
     * @param b2df The Box2DFactory instance.
     * @param object The MapObject representing the location of the NPC on the map.
     */
    public NPC(Box2DFactory b2df, MapObject object) {
        this.b2df = b2df;
        this.object = object;
        this.dialogue = new ArrayList<>();
        setUp();
    }

    private void setUp() {
        Rectangle bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = b2df.getNewBodyDef();
        FixtureDef fdef = b2df.getNewFixtureDef();

        fdef.filter.categoryBits = Constants.NPC_BIT;
        fdef.filter.maskBits = Constants.PLAYER_HITBOX_BIT | Constants.PLAYER_BIT;

        PolygonShape shape = b2df.getNewPolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM, (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);

        Body body = b2df.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Constants.PPM, bounds.getHeight() / 2 / Constants.PPM);
        fdef.shape = shape;
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        setMessage((String) object.getProperties().get("message"));
        setName((String) object.getProperties().get("name"));

        updateDialogue();
        shape.dispose();
    }

    /**
     * Returns the message of the NPC in the form of a list of strings.
     * @return The message of the NPC.
     */
    public ArrayList<String> getDialogue() {
        return dialogue;
    }

    /**
     * Sets the message of the NPC.
     * 
     * @param message The new message of the NPC.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the name of the NPC
     * 
     * @param name The new name of the NPC.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the NPC.
     * @return The name of the NPC.
     */
    public String getName() {
        return name;
    }

    public void updateDialogue() {
        dialogue.clear();
        if (!message.isEmpty()) {
            String[] splitDialogue = message.split(":");
            Collections.addAll(dialogue, splitDialogue);
        }
    }

}
