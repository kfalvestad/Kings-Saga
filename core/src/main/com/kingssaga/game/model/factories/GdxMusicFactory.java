package com.kingssaga.game.model.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GdxMusicFactory implements MusicFactory {

    @Override
    public Music createMusic(String filename) {
        return Gdx.audio.newMusic(Gdx.files.internal(filename));
    }
    
}
