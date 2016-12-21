package com.nikitosh.spbau.strategies.inputlisteners;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.strategies.inputlisteners.*;
import com.nikitosh.spbau.model.world.*;
import org.junit.*;

import java.awt.*;
import java.awt.event.*;

import static junitx.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//CheckStyle:OFF: MagicNumber

public class KeyboardInputListenerTest {

    @Test
    public void testGetMove() {
        Hero hero = mock(Hero.class);
        World world = mock(World.class);

        KeyboardInputListener inputListener = new KeyboardInputListener();
        Button button = new Button();
        button.addKeyListener(inputListener);

        KeyEvent event3 = new KeyEvent(button, 1, 20, 1, 10, '3');
        KeyEvent eventW = new KeyEvent(button, 1, 20, 1, 10, 'w');

        inputListener.keyPressed(event3);
        inputListener.keyPressed(eventW);
        assertEquals(Movement.UP, inputListener.getMove(hero, world));
        verify(hero).applyItemFromInventory(2);

        KeyEvent eventQ = new KeyEvent(button, 1, 20, 1, 10, 'q');
        KeyEvent eventA = new KeyEvent(button, 1, 20, 1, 10, 'a');

        inputListener.keyPressed(eventQ);
        inputListener.keyPressed(eventA);
        assertEquals(Movement.LEFT, inputListener.getMove(hero, world));
    }
}

//CheckStyle:ON: MagicNumber
