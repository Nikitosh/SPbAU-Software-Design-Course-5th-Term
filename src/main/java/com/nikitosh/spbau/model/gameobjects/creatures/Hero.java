package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

import java.util.*;

public class Hero extends Creature {
    /**
     * Hero attributes.
     */
    private static final int HEALTH = 10;
    private static final int ATTACK = 2;
    private static final int DEFENSE = 1;
    private static final Attributes ATTRIBUTES = new Attributes.Builder()
            .health(HEALTH)
            .attack(ATTACK)
            .defense(DEFENSE)
            .build();

    /**
     * List of all hero's items at the moment.
     */
    private List<Item> inventory = new ArrayList<>();

    public Hero(Position position) {
        super(position, ATTRIBUTES);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Adds item to inventory.
     *
     * @param item item to be added to inventory.
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Applies item from inventory by index.
     *
     * @param index index of item in the inventory item's list should be applied.
     */
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
