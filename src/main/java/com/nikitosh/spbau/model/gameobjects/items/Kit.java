package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public class Kit extends Item {
    private static final String NAME = "Kit";
    private static final int HEALTH = 5;
    private static final Attributes ATTRIBUTES = new Attributes.Builder()
            .health(HEALTH)
            .build();

    public Kit(Position position) {
        super(position, ATTRIBUTES, NAME);
        singleUse = true;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
