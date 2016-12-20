package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public class Shield extends Item {
    /**
     * Shield attributes.
     */
    private static final String NAME = "Shield";
    private static final int ATTACK = -1;
    private static final int DEFENSE = 2;
    private static final Attributes ATTRIBUTES = new Attributes.Builder()
            .attack(ATTACK)
            .defense(DEFENSE)
            .build();

    public Shield(Position position) {
        super(position, ATTRIBUTES, NAME);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
