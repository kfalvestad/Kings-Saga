package com.kingssaga.game.view;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.GraphicsFactory;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

public class DialogueBox extends Window {

    private Window dialogueBox;
    private Table contentTable;
    private Label dialogueLabel;

    /**
     * Constructs a new DialogueBox object with the specified title and Skin. The DialogueBox is initially hidden.
     * 
     * @param title The title of the DialogueBox.
     * @param graphicsFactory The GraphicsFactory instance which provides the Skin for the DialogueBox.
     */
    public DialogueBox(String title, GraphicsFactory graphicsFactory) {

        super(title, graphicsFactory.getStandardSkin());

        dialogueBox = this;
        contentTable = new Table(); // Create a new Table for content
        dialogueLabel = new Label("", new Label.LabelStyle(graphicsFactory.getStandardFont(), Color.WHITE));
        contentTable.add(dialogueLabel).width(Constants.V_WIDTH - 100).pad(5);
        
        dialogueBox.add(contentTable).expand().fill(); // Add contentTable to the Window
        dialogueBox.setWidth(Constants.V_WIDTH - 80); // Set the width of the Window
        dialogueBox.setHeight(Constants.V_HEIGHT/10); // Set the height of the Window
        dialogueBox.setPosition(Constants.V_WIDTH / 2 - dialogueBox.getWidth() / 2, 100);
        dialogueBox.setVisible(false); // Initially hide the dialogue box
    }

    /**
     * Sets the title of the DialogueBox.
     * 
     * @param title The new title of the DialogueBox.
     */
    public void setTitle(String title) {
        dialogueBox.getTitleLabel().setText(title);
    }

    /**
     * Sets the text of the dialogue label which is displayed as dialogue in our game.
     * 
     * @param text The new text to display in the dialogue label.
     */
    public void setNewText(String text) {
        dialogueLabel.setText(text);
    }

    /**
     * Performs a dialogue with the specified list of dialogue strings, by adding a clicker to the window. Then the player can click to proceed to the next dialogue string.
     * 
     * @param dialogue The list of dialogue strings to display.
     */
    public void performDialogue(ArrayList<String> dialogue) {
        dialogueBox.clearListeners();
        setNewText(dialogue.get(0));
        
        dialogueBox.addListener(new ClickListener(){
            int counter = 0;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( counter < dialogue.size() - 1) {
                    counter++;
                    setNewText(dialogue.get(counter));
                }
                else {
                    hide();
                    dialogueBox.removeListener(this);
                }
            }
        });
    }

    /**
     * Shows the dialogue box.
     */
    public void show() {
        dialogueBox.setVisible(true);
    }

    /**
     * Hides the dialogue box.
     */
    public void hide() {
        setNewText("");
        dialogueBox.setVisible(false);
    }

}

    

