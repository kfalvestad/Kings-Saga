package com.kingssaga.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.*;
import com.kingssaga.game.Constants;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.actors.Stats;
import com.kingssaga.game.model.factories.GraphicsFactory;

import java.util.ArrayList;

/**
 * The Hud class represents the heads-up display in the game.
 * It displays the player's health and coin count.
 */
public class Hud implements Disposable {
    public final Stage stage;
    private Integer playerHealth;
    private Integer coins;
    private final Stats playerStats;
    private final DialogueBox dialogueBox;
    private TextButton BuyHealthButton;
    private TextButton BuyBootsButton;
    private GraphicsFactory graphicsFactory;
    

    final LabelStyle labelStyle;
    final Label playerHealthLabel;
    final Label coinLabel;

    private final Skin skin;
    private final BitmapFont font;
    private final SoundPlayer sp;
    
    /**
     * Constructs a new Hud object.
     * 
     * @param batch The SpriteBatch used for rendering.
     * @param manager The GameManager instance.
     */
    public Hud(SpriteBatch batch, ViewableModel manager, SoundPlayer sp) {
        this.sp = sp;
        this.playerStats = manager.getPlayerStats();
        this.playerHealth = playerStats.getHealth();
        this.coins = playerStats.getCoins();
        this.graphicsFactory = new GraphicsFactory();
        Viewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, batch);

        font = graphicsFactory.getStandardFont();
        skin = graphicsFactory.getStandardSkin();
        labelStyle = graphicsFactory.getNewHudLabelStyle(font);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        playerHealthLabel = new Label("HP: " + (String.format("%03d", playerHealth)), labelStyle);
        coinLabel = new Label("Coins: " + String.format("%06d", coins), labelStyle);

        table.add(playerHealthLabel).expandX().padTop(10).padLeft(10).align(Align.left);
        table.add(coinLabel).expandX().padTop(10).padRight(10).align(Align.right);

        dialogueBox = new DialogueBox("Dialogue", graphicsFactory);
        
        initiateShopButtons();

        Gdx.input.setInputProcessor(stage);

        stage.addActor(dialogueBox);
        stage.addActor(table);
        
    }

    /**
     * Updates the Hud with the latest player health and coin count.
     */
    public void update() {
        playerHealth = playerStats.getHealth();
        coins = playerStats.getCoins();
        playerHealthLabel.setText("HP: " + (String.format("%03d", playerHealth)));
        coinLabel.setText("Coins: " + String.format("%06d", coins));
    }

    /**
     * Disposes of any resources used by the Hud.
     */
    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        graphicsFactory.dispose();
    }

    /**
     * Shows the dialogue box with the name of the NPC at the top and the text in the middle.
     * 
     * @param text The text to display in the dialogue box.
     * @param name The name of the NPC speaking.
     */
    public void showDialogue(ArrayList<String> text, String name) {
        if (name.equals("The Shop Keeper")) {
            stage.addActor(BuyHealthButton);
            stage.addActor(BuyBootsButton);
        }
        dialogueBox.setTitle(name);
        dialogueBox.performDialogue(text);
        dialogueBox.show();
    }

    /**
     * Hides the dialogue box and the shop buttons.
     */
    public void hideDialogue() {
        dialogueBox.hide();
        BuyHealthButton.remove();
        BuyBootsButton.remove();
    }

    private void initiateShopButtons() {
        TextButtonStyle textButtonStyle = skin.get(TextButtonStyle.class);
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetY = -1;

        textButtonStyle.up = skin.getDrawable("default-rect"); 
        textButtonStyle.down = skin.getDrawable("default-rect-down");
        textButtonStyle.over = skin.getDrawable("default-select"); 

        BuyHealthButton = new TextButton("Health Potion - 40 Gold", textButtonStyle);
        BuyBootsButton = new TextButton("Running Boots - 500 Gold", textButtonStyle);

        BuyHealthButton.setPosition(Constants.V_WIDTH / 2 -200, 220);
        BuyBootsButton.setPosition(Constants.V_WIDTH / 2 -200, 170);

        BuyHealthButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerStats.getCoins() >= 40 && playerStats.getHealth() < Constants.PLAYER_MAX_HEALTH) {
                    sp.playSound("coinSound.wav");
                    playerStats.removeCoins(40);
                    playerStats.setHealth(playerStats.getHealth() + 20);
                }
                else if (playerStats.getHealth() >= Constants.PLAYER_MAX_HEALTH) {
                    dialogueBox.setNewText("You are already at full health.");
                }
                else {
                    dialogueBox.setNewText("Not enough coins, you need 40.");
                }
            }
            });
        
        BuyBootsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerStats.getCoins() >= 500 && !playerStats.hasRunningBoots()) {
                    sp.playSound("coinSound.wav");
                    playerStats.removeCoins(500);
                    playerStats.giveRunningBoots();
                } else if (playerStats.hasRunningBoots()) {
                    dialogueBox.setNewText("You already have the Running Boots.");
                }
                else {
                    dialogueBox.setNewText("Not enough coins, you need 500.");
                }
            }
        });
    }

}
