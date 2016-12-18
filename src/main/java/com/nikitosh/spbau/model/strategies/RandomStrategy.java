package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

import java.util.*;

public class RandomStrategy implements Strategy {
    private Random random = new Random();

    @Override
    public Position.Movement getMove(Creature creature, World world) {
        return Position.Movement.values()[random.nextInt(Position.Movement.values().length)];
    }
}
