package com.nikitosh.spbau.model.gameobjects;

import com.nikitosh.spbau.model.world.Position;

public abstract class GameObject {
    protected Position position;

    public Position getPosition() {
        return position;
    }
}
