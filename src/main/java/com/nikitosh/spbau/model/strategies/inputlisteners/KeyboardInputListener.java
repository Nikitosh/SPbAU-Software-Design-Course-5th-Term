package com.nikitosh.spbau.model.strategies.inputlisteners;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.strategies.*;
import com.nikitosh.spbau.model.world.*;

import java.awt.event.*;
import java.util.*;

public class KeyboardInputListener extends KeyAdapter implements Strategy {
    private static final Map<Character, Movement> MOVEMENTS = new HashMap<Character, Movement>() {{
        put('w', Movement.UP);
        put('s', Movement.DOWN);
        put('a', Movement.LEFT);
        put('d', Movement.RIGHT);
    }};

    private Queue<KeyEvent> pressedKeys = new LinkedList<>();

    @Override
    public Movement getMove(Creature creature, World world) {
        synchronized (pressedKeys) {
            while (true) {
                while (pressedKeys.isEmpty()) {
                    try {
                        pressedKeys.wait();
                    } catch (InterruptedException exception) {
                        break;
                    }
                }
                KeyEvent event = pressedKeys.remove();
                Movement movement = processEvent(event);
                if (movement != null) {
                    return movement;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        synchronized (pressedKeys) {
            pressedKeys.add(event);
            pressedKeys.notify();
        }
    }

    private Movement processEvent(KeyEvent event) {
        char symbol = event.getKeyChar();
        if (MOVEMENTS.containsKey(symbol)) {
            return MOVEMENTS.get(symbol);
        }
        return null;
    }
}
