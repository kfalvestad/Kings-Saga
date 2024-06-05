package com.kingssaga.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kingssaga.game.Constants;
import com.kingssaga.game.KingsSaga;

/**
 * The screen that is displayed when the game is won.
 */
public class GameWonScreen implements Screen {

    private final KingsSaga game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final BitmapFont font;

    /**
     * Constructs a new GameWonScreen.
     *
     * @param game The game instance.
     */
    public GameWonScreen(final KingsSaga game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.5f, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        float xPos = viewport.getWorldWidth() / 2;
        float yPos = viewport.getWorldHeight() / 2;

        batch.begin();
        font.draw(batch, "Game Won!", xPos, yPos,
                0, Align.center, false);
        font.draw(batch, "Tap anywhere to restart!", xPos, yPos - 50,
                0, Align.center, false);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
