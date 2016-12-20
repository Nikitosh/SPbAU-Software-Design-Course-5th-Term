package com.nikitosh.spbau.model.gameobjects;

import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public abstract class GameObject {
    protected Position position;
    protected Attributes attributes;

    public GameObject(Position position, Attributes attributes) {
        this.position = position;
        this.attributes = attributes;
    }

    public abstract void accept(Visitor visitor);

    public Attributes getAttributes() {
        return attributes;
    }

    public Position getPosition() {
        return position;
    }
}
