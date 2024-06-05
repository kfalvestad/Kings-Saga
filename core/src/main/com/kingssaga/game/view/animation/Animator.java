package com.kingssaga.game.view.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This class is used to create the animations.
 * Created similarly to <a href="https://libgdx.com/wiki/graphics/2d/2d-animation">...</a>
 */
public class Animator implements Disposable {
    private Texture spriteSheet;
    private SpriteBatch batch;
    private float stateTime;
    private Animation<TextureRegion> animation;
    private final Viewport viewPort;

    public Animator(SpriteBatch batch, String spriteSheetFileName, int frame_cols, int frame_rows, float fps, Viewport viewPort) {
        this.batch = batch;
        this.spriteSheet = new Texture(Gdx.files.internal(spriteSheetFileName));
        this.viewPort = viewPort;

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / frame_cols,
                spriteSheet.getHeight() / frame_rows);

        TextureRegion[] animationFrames = new TextureRegion[frame_cols * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_cols; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<>(1 / fps, animationFrames);
        stateTime = 0f;
    }

    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float viewWidth = viewPort.getWorldWidth();
        float viewHeight = viewPort.getWorldHeight();
    
        batch.draw(currentFrame, 0, 0, viewWidth, viewHeight);
    }

    public void dispose() {
        spriteSheet.dispose();
    }

}
