package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;

public abstract class Creature extends GameObject {
    protected Attributes attributes;

    public void move(WorldMap map, Movement movement) {
        position.move(map, movement);
    }
}
