package com.kingssaga.game;
import com.kingssaga.game.controller.musicController.MusicController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KingsSagaTest {

    private KingsSaga kingsSaga;
    private MusicController musicController;

    @BeforeEach
    void setUp() {
        musicController = mock(MusicController.class);
        kingsSaga = new KingsSaga();

        kingsSaga.introMusic = musicController;
        kingsSaga.atmosphereMusic = musicController;
    }

    @Test
    void shouldUpdateIntroMusicOnRender() {
        kingsSaga.render();

        verify(musicController).update();
    }

    @Test
    void shouldReturnCurrentGameState() {
        GameState expectedState = GameState.MENU;
        kingsSaga.setState(expectedState);

        GameState actualState = kingsSaga.getState();

        assertEquals(expectedState, actualState);
    }

    @Test
    void shouldSetGameState() {
        GameState expectedState = GameState.ACTIVE;

        kingsSaga.setState(expectedState);

        assertEquals(expectedState, kingsSaga.getState());
    }
}