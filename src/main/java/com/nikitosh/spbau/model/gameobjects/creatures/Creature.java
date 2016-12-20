package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;

/**
 * Class for different creatures in our game(and for user's hero too).
 */

public abstract class Creature extends GameObject {
    public Creature(Position position, Attributes attributes) {
        super(position, attributes);
    }

    /**
     * Moves on map according to given movement.
     *
     * @param map world's map.
     * @param movement movement we want to perform.
     */
    public void move(WorldMap map, Movement movement) {
        position.move(map, movement);
    }

    /**
     * Applies (or cancel action) given item to creature.
     *
     * @param item item to be applied.
     * @param toActivate shows whether it should be applying or canceling action.
     */
    public void applyItem(Item item, boolean toActivate) {
        int sign = 1;
        //if we disable, we can just add all parameters with minus before them.
        if (!toActivate) {
            sign = -1;
        }
        Attributes itemAttributes = item.getAttributes();
        attributes.increaseHealth(itemAttributes.getHealth() * sign);
        attributes.increaseAttack(itemAttributes.getAttack() * sign);
        attributes.increaseDefense(itemAttributes.getDefense() * sign);
        item.setActivated(toActivate);
    }

    /**
     * Determines whether the creature is alive.
     *
     * @return is creature alive or not.
     */
    public boolean isAlive() {
        return attributes.getHealth() > 0;
    }

    /**
     * Attacks another creature.
     *
     * @param creature creature to attack.
     */
    public void attack(Creature creature) {
        creature.getAttributes().increaseHealth(
                Math.min(0, creature.getAttributes().getDefense() - attributes.getAttack()));
    }
}
