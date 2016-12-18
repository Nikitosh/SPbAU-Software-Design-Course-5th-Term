package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.*;

public class Troll extends Mob {
    private static final int HEALTH = 10;
    private static final int ATTACK = 2;
    private static final Attributes ATTRIBUTES = new Attributes.Builder(HEALTH)
            .attack(ATTACK)
            .build();

    public Troll(Position position) {
        super(position, ATTRIBUTES);
    }
}
