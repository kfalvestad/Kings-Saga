package com.kingssaga.game.controller.musicController;

import com.badlogic.gdx.audio.Music;

/**
 * Interface for the musicController class.
 * The music controller is used to control the music in the game
 * and to provide a way to fade in and out the music.
 */
public interface IMusicController extends Music {
    
    /**
     * Fades in the music. 
     * The fade calculates the volume change per frame
     * such that the music will fade in over the specified time.
     * As of now, the calculation for the fade time is 
     * hardcoded to use 60 frames per second.
     */
    void fadeIn();

    /**
     * Fades out the music. 
     * The fade calculates the volume change per frame 
     * such that the music will fade out over the specified time.
     * As of now, the calculation for the fade time is 
     * hardcoded to use 60 frames per second.
     */
    void fadeOut();

    /**
     * Gets the volume of the music.
     * @return volume of the music
     */
    float getVolume();

    /**
     * Sets the volume of the music both in the music controller and in the music object.
     * @param volume the volume to set
     */
    void setVolume(float volume);

    /**
     * Sets the fade time for the music.
     * @param time the time to set
     */
    void setFadeTime(float time);

    /**
     * Updates the music controller.
     * This method is called every frame to update the music controller.
     */
    void update();

}
