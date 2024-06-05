package com.kingssaga.game.model.factories;

import com.badlogic.gdx.audio.Music;

public interface MusicFactory {
    Music createMusic(String filename);
}
