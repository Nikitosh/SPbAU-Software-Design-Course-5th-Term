package com.nikitosh.spbau.model.gameobjects;

import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

/**
 * Base class for all objects in our Roguelike.
 */

public abstract class GameObject {
    /**
     * Holds position of object on world's map.
     */
    protected Position position;
    /**
     * Holds attributes of object.
     */
    protected Attributes attributes;

    /**
     * Creates GameObject on given position and given attributes.
     *
     * @param position position on world's map where creature should be created.
     * @param attributes attributes of creature.
     */
    public GameObject(Position position, Attributes attributes) {
        this.position = position;
        this.attributes = attributes;
    }

    /**
     * Method for using "Visitor" pattern.
     *
     * @param visitor visitor we want to handle current object.
     */
    public abstract void accept(Visitor visitor);

    public Attributes getAttributes() {
        return attributes;
    }

    public Position getPosition() {
        return position;
    }
}
