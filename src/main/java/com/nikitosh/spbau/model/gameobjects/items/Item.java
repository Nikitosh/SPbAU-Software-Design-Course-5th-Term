package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;


/**
 * Class for different items in our game.
 * Hero can enable and disable item's features.
 */

public abstract class Item extends GameObject {
    /**
     * Shows whether item is activated at the moment.
     */
    protected boolean activated = false;
    /**
     * Shows whether item is single-use or hero can apply it multiple times.
     */
    protected boolean singleUse = false;
    /**
     * Name of item.
     */
    protected String name;

    public Item(Position position, Attributes attributes, String name) {
        super(position, attributes);
        this.name = name;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isSingleUse() {
        return singleUse;
    }

    public String getName() {
        return name;
    }
}
