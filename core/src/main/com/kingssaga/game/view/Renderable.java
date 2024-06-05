package com.kingssaga.game.view;

public interface Renderable {

    /**
     * This method is used to simulate depth. Classes that implement Renderable needs to calculate and return a z-value,
     * which is then used to sort the render order.
     * @return the z-value of the object
     */
    float getZ();
}
