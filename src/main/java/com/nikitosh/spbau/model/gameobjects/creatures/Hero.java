package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.*;

public class Hero extends Creature {
    private static final int HEALTH = 10;
    private static final int ATTACK = 1;
    private static final int DEFENSE = 1;
    private static final Attributes ATTRIBUTES = new Attributes.Builder(HEALTH)
            .attack(ATTACK)
            .defense(DEFENSE)
            .build();

    public Hero(Position position) {
        this.position = position;
        this.attributes = ATTRIBUTES;
    }
}
