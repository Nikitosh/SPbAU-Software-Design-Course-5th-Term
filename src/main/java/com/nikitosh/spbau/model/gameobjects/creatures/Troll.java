package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public class Troll extends Mob {
    /**
     * Troll attributes.
     */
    private static final int HEALTH = 10;
    private static final int ATTACK = 2;
    private static final Attributes ATTRIBUTES = new Attributes.Builder()
            .health(HEALTH)
            .attack(ATTACK)
            .build();

    public Troll(Position position) {
        super(position, ATTRIBUTES);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
