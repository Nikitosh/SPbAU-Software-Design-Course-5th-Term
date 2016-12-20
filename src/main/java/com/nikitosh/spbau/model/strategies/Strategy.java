package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

/**
 * Base interface for interaction implementation.
 * Could be used for creating new strategies for mobs.
 * Could be used for creating new user's controller.
 *
 * Implements "Strategy" pattern.
 */

public interface Strategy {
    /**
     * Get Movement from current position of given creature.
     *
     * @param creature creature to be moved.
     * @param world world description with map and list of another creatures.
     * @return Movement to be performed.
     */
    Movement getMove(Creature creature, World world);
}
