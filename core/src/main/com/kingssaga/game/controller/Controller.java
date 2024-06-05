package com.kingssaga.game.controller;

import com.badlogic.gdx.Gdx;
import com.kingssaga.game.Constants;

/**
 * The controller class is responsible for listening to user input and updating the model accordingly.
 */
public class Controller {

    ControllableModel model;

    /**
     * Constructs a new Controller with the specified model.
     *
     * @param model The model that this controller will update.
     */
    public Controller (ControllableModel model) {
        this.model = model;
    }

    /**
     * Checks if movement keys are pressed and updates the model accordingly.
     * The keys for movement are defined in the Constants class.
     */
    private void movement() {

        boolean right = Gdx.input.isKeyPressed(Constants.MOVE_RIGHT);
        boolean left = Gdx.input.isKeyPressed(Constants.MOVE_LEFT);
        boolean up = Gdx.input.isKeyPressed(Constants.MOVE_UP);
        boolean down = Gdx.input.isKeyPressed(Constants.MOVE_DOWN);
        
        model.movePlayer(right, left, up, down);
    }

    /**
     * Checks if the attack key is pressed and updates the model accordingly.
     * The key for attack is defined in the Constants class.
     */
    private void attack() {
        if (Gdx.input.isKeyJustPressed(Constants.ATTACK)) {
            model.playerAttack();
        }
    }

    /**
     * Listens for user input and updates the model accordingly.
     * This method should be called in the game loop.
     */
    public void listen() {
        movement();
        attack();
    }
}
