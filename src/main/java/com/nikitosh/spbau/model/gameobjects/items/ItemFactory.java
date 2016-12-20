package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.world.*;

import java.util.*;

/**
 * Factory which creates items, implements "Factory" pattern.
 */

public class ItemFactory {
    /**
     * Type of item could be created.
     */
    public enum ItemType {
        KIT,
        SHIELD
    };

    /**
     * Creates item on given position.
     *
     * @param position position on world's map where item should be created.
     * @param itemType type of item should be created.
     * @return created item.
     */
    public Item create(Position position, ItemType itemType) {
        switch (itemType) {
            case KIT:
                return new Kit(position);
            case SHIELD:
                return new Shield(position);
            default:
                throw new RuntimeException("Couldn't create item with type: " + itemType);
        }
    }

    /**
     * Creates random item on given position.
     *
     * @param position position on world's map where item should be created.
     * @param random random which gives us type of item should be created.
     * @return created item.
     */
    public Item createRandom(Position position, Random random) {
        return create(position, ItemType.values()[random.nextInt(ItemType.values().length)]);
    }
}
