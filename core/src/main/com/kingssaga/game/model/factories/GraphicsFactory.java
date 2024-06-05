package com.kingssaga.game.model.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GraphicsFactory {

    private final AssetManager assetsManager;

    public GraphicsFactory() {
        this.assetsManager = new AssetManager();
        assetsManager.load("UI/kings_font.fnt", BitmapFont.class);
        assetsManager.finishLoading();
    }

    public AssetManager getAssetsManager() {
        return assetsManager;
        
    }

    /**
     * Creates a new Sprite object using the provided Texture.
     *
     * @param texture The Texture to create the Sprite from.
     * @return A new Sprite object.
     */
    public Sprite getNewSprite(Texture texture) {
        return new Sprite(texture);
    }

    /**
     * Creates a new Texture object using the specified file path.
     * If an exception occurs while loading the texture, a default texture ("item.png") is used instead.
     *
     * @param path The file path of the texture.
     * @return A new Texture object.
     */
    public Texture getNewTexture(String path) {
        try {
            return new Texture(Gdx.files.internal(path));
        } catch (Exception e) {
            return new Texture(Gdx.files.internal("item.png"));
        }
    }

    /**
     * Returns a standard skin for the user interface.
     *
     * @return a new instance of the Skin class with the UI skin loaded from "UI/uiskin.json"
     */
    public Skin getStandardSkin() {
        return new Skin(Gdx.files.internal("UI/uiskin.json"));
    }

    /**
     * Returns the standard font used in the game.
     *
     * @return The standard font as a BitmapFont object.
     */
    public BitmapFont getStandardFont() {
        BitmapFont font = assetsManager.get("UI/kings_font.fnt", BitmapFont.class);
        font.getData().setScale(0.4f);
        return font;
    }

    /**
     * Creates a new LabelStyle object using the provided BitmapFont and Salmon Color.
     * 
     * @param font The BitmapFont to use.
     * @return A new LabelStyle object.
    */
    public LabelStyle getNewHudLabelStyle(BitmapFont font) {
        return new LabelStyle(font, Color.SALMON);
    }

    public void dispose() {
        assetsManager.dispose();
    }

}
