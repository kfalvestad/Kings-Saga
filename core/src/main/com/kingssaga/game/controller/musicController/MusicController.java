package com.kingssaga.game.controller.musicController;

import com.badlogic.gdx.audio.Music;
import com.kingssaga.game.model.factories.MusicFactory;

/**
 * A wrapper class for the music interface.
 * The music controller is used to control the music in the game
 * and to provide a way to fade in and out the music.
 */
public class MusicController implements IMusicController {
    private final Music music;
    private float volume = 1.0f;
    private boolean isFadingOut = false;
    private boolean isFadingIn = false;
    private float fadeTime = 3.0f;
    private float volumeChangePerFrame;

    public MusicController(MusicFactory factory, String filename) {
        this.music = factory.createMusic(filename);
        if (music == null) {
            throw new IllegalArgumentException("Music file not found: " + filename);
        }
    }

    @Override
    public void setFadeTime(float time) {
        this.fadeTime = time;
    }

    @Override
    public void fadeIn() {
        if (music.isPlaying()) {
            return;
        }
        if (fadeTime != 0.0) {
            setVolume(0);
            music.setVolume(volume);
            music.play();
            isFadingIn = true;
            volumeChangePerFrame = 1 / (fadeTime * 60);
        }
    }

    @Override
    public void fadeOut() {
        if (fadeTime != 0.0) {
            isFadingIn = false;
            isFadingOut = true;
            volumeChangePerFrame = volume / (fadeTime * 60);
            // volumeChangePerFrame = volume / (fadeTime/Gdx.graphics.getDeltaTime());
        } else {
            music.stop();
        }
    }

    @Override
    public void update() {
        if (isFadingIn) {
            float nextVolume = volume + volumeChangePerFrame;
            if (nextVolume > 1) {
                nextVolume = 1;
                isFadingIn = false;
            } else {
                setVolume(nextVolume);
            }
            setVolume(nextVolume);
        } else if (isFadingOut) {
            float nextVolume = volume - volumeChangePerFrame;
            if (nextVolume < 0) {
                nextVolume = 0;
                isFadingOut = false;
                music.stop();
            } else {
                setVolume(nextVolume);
            }
            setVolume(nextVolume);
        }
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
        music.setVolume(volume);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    // The following methods are just wrappers for the music object

    @Override
    public void play() {
        music.play();
    }

    @Override
    public void stop() {
        music.stop();
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public boolean isPlaying() {
        return music.isPlaying();
    }

    @Override
    public void setLooping(boolean isLooping) {
        music.setLooping(isLooping);
    }

    @Override
    public boolean isLooping() {
        return music.isLooping();
    }

    @Override
    public void setPan(float pan, float volume) {
        music.setPan(pan, volume);
    }

    @Override
    public void setPosition(float position) {
        music.setPosition(position);
    }

    @Override
    public float getPosition() {
        return music.getPosition();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        music.setOnCompletionListener(listener);
    }

    public Music getMusic() {
        return music;
    }
    
    public boolean isFadingIn() {
        return isFadingIn;
    }

    public boolean isFadingOut() {
        return isFadingOut;
    }
}
