package com.kingssaga.game.controller;

/**
 * This interface represents a model that can be controlled, typically by a player.
 * It provides methods to move the player and to perform an attack.
 */
public interface ControllableModel {

     /**
      * Moves the player in the specified directions.
      *
      * @param right If true, moves the player to the right.
      * @param left If true, moves the player to the left.
      * @param up If true, moves the player upwards.
      * @param down If true, moves the player downwards.
      */
     void movePlayer(boolean right, boolean left, boolean up, boolean down);

     /**
      * Makes the player perform an attack.
      */
     void playerAttack();
}
