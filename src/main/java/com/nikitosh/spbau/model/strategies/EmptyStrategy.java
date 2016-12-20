package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

/**
 * Implements empty strategy, for every creature it just skips it's turns.
 */

public class EmptyStrategy implements Strategy {
    @Override
    public Movement getMove(Creature creature, World world) {
        return Movement.NONE;
    }
}
