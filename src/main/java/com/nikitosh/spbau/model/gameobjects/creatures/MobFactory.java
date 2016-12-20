package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.*;

import java.util.*;

/**
 * Factory which creates mobs, implements "Factory" pattern.
 */

public class MobFactory {
    /**
     * Type of mob that could be created.
     */
    public enum MobType {
        ORC,
        TROLL
    };

    /**
     * Creates mob on given position.
     *
     * @param position position on world's map where mob should be created.
     * @param mobType type of mob should be created.
     * @return created mob.
     */
    public Mob create(Position position, MobType mobType) {
        switch (mobType) {
            case ORC:
                return new Orc(position);
            case TROLL:
                return new Troll(position);
            default:
                throw new RuntimeException("Couldn't create mob with type: " + mobType);
        }
    }

    /**
     * Creates random mob on given position.
     *
     * @param position position on world's map where mob should be created.
     * @param random random which gives us type of mob should be created.
     * @return created mob.
     */
    public Mob createRandom(Position position, Random random) {
        return create(position, MobType.values()[random.nextInt(MobType.values().length)]);
    }
}
