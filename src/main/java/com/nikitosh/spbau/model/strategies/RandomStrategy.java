package com.nikitosh.spbau.model.strategies;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

import java.util.*;

public class RandomStrategy implements Strategy {
    private Random random = new Random();

    @Override
    public Movement getMove(Creature creature, World world) {
        return Movement.values()[random.nextInt(Movement.values().length)];
    }
}
