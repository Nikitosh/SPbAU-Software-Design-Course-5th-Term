package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

import java.util.*;

public class Hero extends Creature {
    private static final int HEALTH = 10;
    private static final int ATTACK = 2;
    private static final int DEFENSE = 1;
    private static final Attributes ATTRIBUTES = new Attributes.Builder()
            .health(HEALTH)
            .attack(ATTACK)
            .defense(DEFENSE)
            .build();

    private List<Item> inventory = new ArrayList<>();

    public Hero(Position position) {
        super(position, ATTRIBUTES);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void applyItemFromInventory(int index) {
        if (index >= inventory.size()) {
            return;
        }
        Item item = inventory.get(index);
        boolean activated = !item.isActivated();
        applyItem(item, activated);

        if (item.isSingleUse()) {
            inventory.remove(item);
        }
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
