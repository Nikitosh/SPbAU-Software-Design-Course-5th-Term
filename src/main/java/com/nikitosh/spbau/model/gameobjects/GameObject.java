package com.nikitosh.spbau.model.gameobjects;

import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

public abstract class GameObject {
    protected Position position;

    public abstract void accept(Visitor visitor);

    public Position getPosition() {
        return position;
    }
}
