package com.nikitosh.spbau.model.world;

import com.nikitosh.spbau.model.gameobjects.creatures.*;

import java.util.*;

public class World {
    private int MOB_AMOUNT = 3;
    private WorldMap map;
    private Hero hero;
    private List<Mob> mobs;
    private List<Position> emptyPositions;
    private Random random = new Random();
    private MobFactory mobFactory = new MobFactory();

    public World(WorldMap map) {
        this.map = map;
        getEmptyPositions();
        generateHero();
        for (int i = 0; i < MOB_AMOUNT; i++) {
            generateMob();
        }
    }

    private void generateHero() {
        if (emptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating hero");
        }
        Position heroPosition = emptyPositions.get(random.nextInt(emptyPositions.size()));
        hero = new Hero(heroPosition);
    }

    private void generateMob() {
        List<Position> currentEmptyPositions = new ArrayList<>();
        for (Position position : emptyPositions) {
            boolean currentEmpty = true;
            if (position.equals(hero.getPosition())) {
                currentEmpty = false;
            }
            for (Mob mob : mobs) {
                if (position.equals(mob.getPosition())) {
                    currentEmpty = false;
                }
            }
            if (currentEmpty) {
                currentEmptyPositions.add(position);
            }
        }
        if (currentEmptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating mob");
        }
        Position mobPosition = currentEmptyPositions.get(random.nextInt(emptyPositions.size()));
        mobs.add(mobFactory.createRandom(mobPosition, random));
    }

    private List<Position> getEmptyPositions() {
        emptyPositions = new ArrayList<>();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.isEmptyCell(x, y)) {
                    emptyPositions.add(new Position(x, y));
                }
            }
        }
        return emptyPositions;
    }
}
