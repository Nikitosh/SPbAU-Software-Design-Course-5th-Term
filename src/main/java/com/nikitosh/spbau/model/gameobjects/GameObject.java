package com.nikitosh.spbau.model.gameobjects;

import com.nikitosh.spbau.model.world.*;

public abstract class GameObject {
    protected Position position;

    public Position getPosition() {
        return position;
    }
}
