package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;

/**
 * Class for different mobs (NPC) in our game.
 */

public abstract class Mob extends Creature {
    public Mob(Position position, Attributes attributes) {
        super(position, attributes);
    }
}
