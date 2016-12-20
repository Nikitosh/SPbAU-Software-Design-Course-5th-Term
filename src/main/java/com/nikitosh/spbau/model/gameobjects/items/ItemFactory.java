package com.nikitosh.spbau.model.gameobjects.items;

import com.nikitosh.spbau.model.world.*;

import java.util.*;

public class ItemFactory {
    public enum ItemType {
        KIT,
        SHIELD
    };

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

    public Item createRandom(Position position, Random random) {
        return create(position, ItemType.values()[random.nextInt(ItemType.values().length)]);
    }
}
