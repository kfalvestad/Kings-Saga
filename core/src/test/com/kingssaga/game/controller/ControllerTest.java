package com.kingssaga.game.controller;
import com.badlogic.gdx.Input;
import com.kingssaga.game.Constants;
import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private ControllableModel model;
    @Mock
    private Input mockInput;

    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Gdx.input = mockInput; // Sett Gdx.input til å være den mocked input
        controller = new Controller(model);
    }

    @Test
    void testMovement_RightPressed() {
        when(mockInput.isKeyPressed(Constants.MOVE_RIGHT)).thenReturn(true);
        when(mockInput.isKeyPressed(Constants.MOVE_LEFT)).thenReturn(false);
        when(mockInput.isKeyPressed(Constants.MOVE_UP)).thenReturn(false);
        when(mockInput.isKeyPressed(Constants.MOVE_DOWN)).thenReturn(false);

        controller.listen();

        verify(model).movePlayer(true, false, false, false);
    }

    @Test
    void testAttack_KeyJustPressed() {
        when(mockInput.isKeyJustPressed(Constants.ATTACK)).thenReturn(true);

        controller.listen();

        verify(model).playerAttack();
    }

    @Test
    void testNoAction_KeysNotPressed() {
    // Om alle keyboard keys er false
    when(mockInput.isKeyPressed(Constants.MOVE_RIGHT)).thenReturn(false);
    when(mockInput.isKeyPressed(Constants.MOVE_LEFT)).thenReturn(false);
    when(mockInput.isKeyPressed(Constants.MOVE_UP)).thenReturn(false);
    when(mockInput.isKeyPressed(Constants.MOVE_DOWN)).thenReturn(false);
    when(mockInput.isKeyJustPressed(Constants.ATTACK)).thenReturn(false);

    controller.listen();

    // Verifiser at movePlayer bare blir kalt med false og playerAttack ikke blir kalt
    verify(model).movePlayer(false, false, false, false);
    verify(model, never()).playerAttack();
}
}