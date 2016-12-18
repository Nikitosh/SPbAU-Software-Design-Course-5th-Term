package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

public class EmptyStrategy implements Strategy {
    @Override
    public Position.Movement getMove(Creature creature, World world) {
        return Position.Movement.NONE;
    }
}
