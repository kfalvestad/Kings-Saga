package com.kingssaga.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kingssaga.game.Constants;
import com.kingssaga.game.GameState;
import com.kingssaga.game.KingsSaga;
import com.kingssaga.game.view.animation.Animator;

/**
 * The main menu screen of the game.
 */
public class MainMenuScreen implements Screen {

    final KingsSaga game;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final Stage stage;
    final OrthographicCamera camera;
    final Animator animation;
    
    /**
     * Constructs a new MainMenuScreen.
     *
     * @param game The game instance.
     */
    public MainMenuScreen(final KingsSaga game) {
        this.game = game;
        this.game.setState(GameState.MENU);
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        this.animation = new Animator(batch, "MainMenuVikingAnimation43format.png", 3, 7, 12, viewport);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        Table table = new Table();
        stage.addActor(table);
        table.setDebug(false);
        table.setSize(viewport.getWorldWidth(), viewport.getWorldHeight() / 5);

        TextButton controlsButton = new TextButton("Controls", skin);
        controlsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Controls", skin);
                dialog.text("Press the keys W, A, S, D to move\nPress E to attack\n\nDefeat the usurper\nand become the king of the vikings!");
                dialog.button("Close");
                dialog.show(stage);
            }
        });
        table.add(controlsButton).size(100, 50).pad(10);

        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(playButton).size(100, 50).pad(10);
    }

    @Override
    public void show() {
        game.introMusic.setFadeTime(3.0f);
        game.introMusic.setLooping(true);
        game.introMusic.fadeIn();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        animation.render();
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        game.introMusic.setFadeTime(1.5f);
        game.introMusic.fadeOut();
    }

    @Override
    public void dispose() {
        this.animation.dispose();
        this.stage.dispose();
    }

}
