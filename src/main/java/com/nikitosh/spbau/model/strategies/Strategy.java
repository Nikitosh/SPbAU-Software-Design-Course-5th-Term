package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

public interface Strategy {
    Position.Movement getMove(Creature creature, World world);
}
