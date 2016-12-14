package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.Position;

public class Mob extends Creature {
    public Mob(Position position, Attributes attributes) {
        this.position = position;
        this.attributes = attributes;
    }
}