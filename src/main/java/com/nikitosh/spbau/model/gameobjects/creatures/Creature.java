package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;

public abstract class Creature extends GameObject {
    public Creature(Position position, Attributes attributes) {
        super(position, attributes);
    }

    public void move(WorldMap map, Movement movement) {
        position.move(map, movement);
    }

    public void applyItem(Item item, boolean toActivate) {
        int sign = 1;
        if (!toActivate) {
            sign = -1;
        }
        Attributes itemAttributes = item.getAttributes();
        attributes.increaseHealth(itemAttributes.getHealth() * sign);
        attributes.increaseAttack(itemAttributes.getAttack() * sign);
        attributes.increaseDefense(itemAttributes.getDefense() * sign);
        item.setActivated(toActivate);
    }

    public boolean isAlive() {
        return attributes.getHealth() > 0;
    }

    public void attack(Creature creature) {
        creature.getAttributes().increaseHealth(
                Math.min(0, creature.getAttributes().getDefense() - attributes.getAttack()));
    }
}
