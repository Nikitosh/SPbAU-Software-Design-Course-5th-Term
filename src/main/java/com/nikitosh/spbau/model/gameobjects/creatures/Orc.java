package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public class Orc extends Mob {
    private static final int HEALTH = 5;
    private static final int ATTACK = 1;
    private static final int DEFENSE = 1;
    private static final Attributes ATTRIBUTES = new Attributes.Builder(HEALTH)
            .attack(ATTACK)
            .defense(DEFENSE)
            .build();

    public Orc(Position position) {
        super(position, ATTRIBUTES);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
