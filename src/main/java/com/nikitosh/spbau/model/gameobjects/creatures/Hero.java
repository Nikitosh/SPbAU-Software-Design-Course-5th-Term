package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.Position;

public class Hero extends Creature {
    public Hero(Position position, Attributes attributes) {
        this.position = position;
        this.attributes = attributes;
    }
}
