package com.kingssaga.game.controller.musicController;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.kingssaga.game.model.factories.MusicFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MusicControllerTest {

    @Mock
    private Music mockMusic;
    @Mock
    private MusicFactory mockFactory;

    private MusicController musicController;
    private final String filename = "test.mp3";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Anta at Gdx.audio.newMusic og Gdx.files.internal er stubbet ut et sted i testoppsettet
        when(mockFactory.createMusic(anyString())).thenReturn(mockMusic);
        musicController = new MusicController(mockFactory, filename);  // Sett inn mock-objektet direkte etter konstruksjon
    }

    @Test
    void fadeIn_StartsMusicPlaying_WhenNotAlreadyPlaying() {
        when(mockMusic.isPlaying()).thenReturn(false);
        musicController.fadeIn();
        verify(mockMusic).play();
        assertTrue(musicController.isFadingIn());
    }

    @Test
    void fadeOut_StopsMusicPlaying_WhenFadeTimeIsZero() {
        musicController.setFadeTime(0);
        musicController.fadeOut();
        verify(mockMusic).stop();
    }

    
    @Test
    void testUpdate_FadeOut() {
        musicController.setVolume(1.0f);
        musicController.setFadeTime(3.0f); // 180 frames
        musicController.fadeOut();

        musicController.update();

        verify(mockMusic, times(3)).setVolume(anyFloat()); // Initial and update
        assertTrue(musicController.isFadingOut());
    }

    @Test
    void testUpdate_FadeOut_Completes() {
        musicController.setVolume(0.1f);
        musicController.setFadeTime(2.0f); // 180 frames
        musicController.fadeOut();

        // Simulate multiple updates until volume should reach 0
        for (int i = 0; i < 180; i++) {
            musicController.update();
        }

        verify(mockMusic).stop();
        assertFalse(musicController.isFadingOut());
    }

    @Test
    void update_AdjustsVolumeDuringFadeIn() {
        musicController.setFadeTime(1); // 1 sekund fade tid
        musicController.fadeIn();
        musicController.update(); // Simulerer en frame-oppdatering
        verify(mockMusic, times(4)).setVolume(anyFloat());
    }

    @Test
    void setVolume_SetsVolumeOnMusicObject() {
        musicController.setVolume(0.5f);
        verify(mockMusic).setVolume(0.5f);
    }

    @Test
    void play_CallsPlayOnMusic() {
        musicController.play();
        verify(mockMusic).play();
    }

    @Test
    void stop_CallsStopOnMusic() {
        musicController.stop();
        verify(mockMusic).stop();
    }

    @Test
    void pause_CallsPauseOnMusic() {
        musicController.pause();
        verify(mockMusic).pause();
    }

    @Test
    void getVolume_ReturnsVolume() {
        musicController.setVolume(0.5f);
        assertEquals(0.5f, musicController.getVolume());
    }

    @Test
    void dispose_CallsDisposeOnMusic() {
        musicController.dispose();
        verify(mockMusic).dispose();
    }

    @Test void isPlaying_ReturnsIsPlaying() {
        when(mockMusic.isPlaying()).thenReturn(true);
        assertTrue(musicController.isPlaying());
    }

    @Test void setLooping_SetsLoopingOnMusic() {
        musicController.setLooping(true);
        verify(mockMusic).setLooping(true);
    }

    @Test void isLooping_ReturnsIsLooping() {
        when(mockMusic.isLooping()).thenReturn(true);
        assertTrue(musicController.isLooping());
    }

    @Test void setPan_SetsPanOnMusic() {
        musicController.setPan(0.5f, 0.5f);
        verify(mockMusic).setPan(0.5f, 0.5f);
    }

    @Test void setPosition_SetsPositionOnMusic() {
        musicController.setPosition(0.5f);
        verify(mockMusic).setPosition(0.5f);
    }

    @Test void getPosition_ReturnsPosition() {
        when(mockMusic.getPosition()).thenReturn(0.5f);
        assertEquals(0.5f, musicController.getPosition());
    }

    @Test void getMusic_ReturnsMusic() {
        assertEquals(mockMusic, musicController.getMusic());
    }

     @Test
    void testSetOnCompletionListener() {
        OnCompletionListener mockListener = mock(OnCompletionListener.class);
        musicController.setOnCompletionListener(mockListener);
        verify(musicController.getMusic()).setOnCompletionListener(mockListener);
    }

    @Test void isFadingOut_ReturnsIsFadingOut() {
        musicController.fadeOut();
        assertTrue(musicController.isFadingOut());
    }

    
}