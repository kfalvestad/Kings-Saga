package com.kingssaga.game.view.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kingssaga.game.*;
import com.kingssaga.game.controller.Controller;
import com.kingssaga.game.model.GameManager;
import com.kingssaga.game.view.Hud;
import java.util.ArrayList;

/**
 * The GameScreen class represents the screen where the game is played.
 * It implements the Screen interface from the LibGDX library.
 */
public class GameScreen implements Screen, SoundPlayer {

  private final KingsSaga game;
  private final SpriteBatch batch;
  private final Hud hud;
  private final OrthographicCamera camera;
  private final Viewport viewport;
  private final OrthogonalTiledMapRenderer renderer;
  //private final Box2DDebugRenderer b2dr;
  private final GameManager manager;
  private final Controller controller;

  /**
   * Constructs a new GameScreen object.
   * 
   * @param game The KingsSaga game instance.
   */
  public GameScreen(final KingsSaga game) {
    this.game = game;
    manager = new GameManager(this);
    controller = new Controller(manager);
    //b2dr = new Box2DDebugRenderer();
    batch = game.getBatch();
    camera = new OrthographicCamera();
    camera.setToOrtho(
      false,
      Constants.V_WIDTH / Constants.PPM,
      Constants.V_HEIGHT / Constants.PPM
    );
    camera.update();
    viewport =
    new FitViewport(
      Constants.V_WIDTH / Constants.PPM,
      Constants.V_HEIGHT / Constants.PPM,
      camera
      );
    renderer = new OrthogonalTiledMapRenderer(manager.getCurrentMap(), 1 / Constants.PPM, batch);
    hud = new Hud(batch, manager, this);
  }

  /**
   * Loads a new TiledMap to be rendered.
   * 
   * @param map The TiledMap to be loaded.
   */
  public void loadNewMap(TiledMap map) {
    renderer.setMap(map);
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);
    manager.update();
    controller.listen();
    manager.calculateNewCameraPosition(camera, viewport);
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    renderer.setView(camera);

    GameState state = game.getState();
    if (state == GameState.GAME_OVER) {
      game.setScreen(new GameOverScreen(game));
    } else if (state == GameState.VICTORY) {
      game.setScreen(new GameWonScreen(game));
    }

    batch.begin();
    manager.draw(batch, renderer);
    batch.end();

    hud.update();
    batch.setProjectionMatrix(hud.stage.getCamera().combined);
    hud.stage.draw();

    //b2dr.render(manager.getWorld(), camera.combined);
  }
  
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }
  
  @Override
  public void show() {
    game.atmosphereMusic.setLooping(true);
    game.atmosphereMusic.play();
  }
  
  @Override
  public void hide() {
    game.atmosphereMusic.stop();
  }

  
  @Override
  public void pause() {
  }
  
  @Override
  public void resume() {
  }
  
  @Override
  public void dispose() {
    manager.dispose();
    renderer.dispose();
   // b2dr.dispose();
    batch.dispose();
    hud.dispose();
  }

  @Override
  public void playSound(String file) {
    game.playSound(file);
  }

  public void showDialogue(ArrayList<String> text, String name) {
    hud.showDialogue(text, name);
  }

  public void hideDialogue() {
    hud.hideDialogue();
  }

  public void setState(GameState state) {
    game.setState(state);
  }
}


