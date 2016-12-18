package com.nikitosh.spbau.model.gameobjects.creatures;

import com.nikitosh.spbau.model.world.*;

import java.util.*;

public class MobFactory {
    public enum MobType {
        ORC,
        TROLL
    };

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

    public Mob createRandom(Position position, Random random) {
        return create(position, MobType.values()[random.nextInt(MobType.values().length)]);
    }
}
