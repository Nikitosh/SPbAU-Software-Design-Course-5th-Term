package com.nikitosh.spbau;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public final class TestUtilities {
    private TestUtilities() {}

    public static Attributes createAttributes(int health, int attack, int defense) {
        return new Attributes.Builder()
                .health(health)
                .attack(attack)
                .defense(defense)
                .build();
    }

    public static Creature createCreature(Position position, Attributes attributes) {
        return new Creature(position, attributes) {
            @Override
            public void accept(Visitor visitor) {}
        };
    }

    public static Item createItem(Position position, Attributes attributes) {
        return new Item(position, attributes, "") {
            @Override
            public void accept(Visitor visitor) {}
        };
    }
}
