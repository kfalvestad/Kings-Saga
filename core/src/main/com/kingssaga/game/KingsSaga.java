package com.kingssaga.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingssaga.game.controller.musicController.MusicController;
import com.kingssaga.game.model.factories.GdxMusicFactory;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kingssaga.game.view.screens.MainMenuScreen;

/**
 * The main class of the KingsSaga game.
 */
public class KingsSaga extends Game {

  private SpriteBatch batch;
  private BitmapFont font; // this is used for drawing fonts (text)
  public MusicController introMusic;
  public MusicController atmosphereMusic;
  private GameState state;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    introMusic = new MusicController(new GdxMusicFactory(), "kingssagatheme.wav");
    atmosphereMusic = new MusicController(new GdxMusicFactory(), "Atmosphere.wav");
    this.state = GameState.MENU;
    this.setScreen(new MainMenuScreen(this));
  }

  /**
   * Get the SpriteBatch used for rendering.
   * 
   * @return The SpriteBatch object.
   */
  public SpriteBatch getBatch() {
    return this.batch;
  }

  /**
   * Get the BitmapFont used for drawing fonts.
   * 
   * @return The BitmapFont object.
   */
  public BitmapFont getFont() {
    return this.font;
  }

  @Override
  public void render() {
    super.render();
    introMusic.update();
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
    introMusic.dispose();
    atmosphereMusic.dispose();
  }

  /**
   * Play a sound effect.
   * 
   * @param filename The filename of the sound effect.
   */
  public void playSound(String filename) {
    Sound sound = Gdx.audio.newSound(Gdx.files.internal(filename));
    sound.play();
  }

  /**
   * Get the current game state.
   * 
   * @return The current GameState.
   */
  public GameState getState() {
    return this.state;
  }

  /**
   * Set the current game state.
   * 
   * @param state The new GameState.
   */
  public void setState(GameState state) {
    this.state = state;
  }
}
