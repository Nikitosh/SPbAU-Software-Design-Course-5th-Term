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
        Hero hero = (Hero) creature;
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
                Movement movement = processEvent(event, hero);
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

    private Movement processEvent(KeyEvent event, Hero hero) {
        char symbol = event.getKeyChar();
        if (MOVEMENTS.containsKey(symbol)) {
            return MOVEMENTS.get(symbol);
        }
        if (isInventoryUsed(symbol)) {
            hero.applyItemFromInventory(symbol - '1');
        }
        return null;
    }

    private boolean isInventoryUsed(char symbol) {
        return symbol >= '1' && symbol <= '9';
    }
}
