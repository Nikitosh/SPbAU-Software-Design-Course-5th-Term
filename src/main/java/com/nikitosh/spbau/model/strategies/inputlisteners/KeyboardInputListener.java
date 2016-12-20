package com.nikitosh.spbau.model.strategies.inputlisteners;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.strategies.*;
import com.nikitosh.spbau.model.world.*;

import java.awt.event.*;
import java.util.*;

/**
 * Implements input controller for keyboard.
 * Uses "wasd" to move and "1-9" to use inventory.
 */

public class KeyboardInputListener extends KeyAdapter implements Strategy {
    private static final Map<Character, Movement> MOVEMENTS = new HashMap<Character, Movement>() {{
        put('w', Movement.UP);
        put('s', Movement.DOWN);
        put('a', Movement.LEFT);
        put('d', Movement.RIGHT);
    }};

    /**
     * Stores not yet handled key events.
     */
    private Queue<KeyEvent> pressedKeys = new LinkedList<>();

    @Override
    public Movement getMove(Creature creature, World world) {
        //It's guaranteed that given creature is hero.
        if (!(creature instanceof Hero)) {
            throw new RuntimeException("Given creature is not a Hero instance");
        }
        Hero hero = (Hero) creature;
        synchronized (pressedKeys) {
            while (true) {
                //Waits until we can make a move.
                while (pressedKeys.isEmpty()) {
                    try {
                        pressedKeys.wait();
                    } catch (InterruptedException exception) {
                        break;
                    }
                }
                KeyEvent event = pressedKeys.remove();
                Movement movement = processEvent(event, hero);
                if (movement != null) {
                    return movement;
                }
            }
        }
    }


    /**
     * Just adds new key events to queue for handling (it will be handled when getMove will be performed).
     *
     * @param event event causes by key pressing.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        synchronized (pressedKeys) {
            pressedKeys.add(event);
            pressedKeys.notify();
        }
    }

    /**
     * Handles key event.
     *
     * @param event key event to handle.
     * @param hero hero to whom some actions could be applied.
     * @return whether given key event was a Movement.
     */
    private Movement processEvent(KeyEvent event, Hero hero) {
        char symbol = event.getKeyChar();
        if (MOVEMENTS.containsKey(symbol)) {
            return MOVEMENTS.get(symbol);
        }
        if (isInventoryKey(symbol)) {
            hero.applyItemFromInventory(symbol - '1');
        }
        return null;
    }

    /**
     * Determines whether symbol activates inventory.
     * @param symbol given symbol to check.
     * @return whether symbol activates inventory.
     */
    private boolean isInventoryKey(char symbol) {
        return symbol >= '1' && symbol <= '9';
    }
}
