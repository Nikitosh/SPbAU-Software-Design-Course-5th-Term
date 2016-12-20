package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.world.*;

public abstract class Item extends GameObject {
    protected boolean activated = false;
    protected boolean singleUse = false;
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
