package com.nikitosh.spbau.model.world;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.strategies.*;

import java.util.*;

public class World {
    private static final int MOBS_AMOUNT = 20;
    private static final int ITEMS_AMOUNT = 3;

    private WorldMap map;
    private Hero hero;
    private List<Mob> mobs = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Position> emptyPositions;
    private Random random = new Random();
    private MobFactory mobFactory = new MobFactory();
    private ItemFactory itemFactory = new ItemFactory();

    public World(WorldMap map) {
        this.map = map;
        getEmptyPositions();
        generateHero();
        for (int i = 0; i < MOBS_AMOUNT; i++) {
            generateMob();
        }
        for (int i = 0; i < ITEMS_AMOUNT; i++) {
            generateItem();
        }
    }

    public void makeTurn(Strategy playerStrategy, Strategy mobsStrategy) {
        Movement heroMovement = playerStrategy.getMove(hero, this);
        hero.move(map, heroMovement);
        List<Movement> mobsMovements = new ArrayList<>();
        for (Mob mob : mobs) {
            mobsMovements.add(mobsStrategy.getMove(mob, this));
        }
        for (int i = 0; i < mobs.size(); i++) {
            mobs.get(i).move(map, mobsMovements.get(i));
        }
        applyItems();
        fightWithMobs();
    }

    public WorldMap getWorldMap() {
        return map;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();
        for (Item item : items) {
            gameObjects.add(item);
        }
        for (Mob mob : mobs) {
            gameObjects.add(mob);
        }
        gameObjects.add(hero);
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

    private void generateHero() {
        if (emptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating hero");
        }
        Position heroPosition = emptyPositions.get(random.nextInt(emptyPositions.size()));
        hero = new Hero(heroPosition);
    }

    private void generateMob() {
        List<Position> currentEmptyPositions = getCurrentEmptyPositions();
        if (currentEmptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating mob");
        }
        Position mobPosition = currentEmptyPositions.get(random.nextInt(currentEmptyPositions.size()));
        mobs.add(mobFactory.createRandom(mobPosition, random));
    }

    private void generateItem() {
        List<Position> currentEmptyPositions = getCurrentEmptyPositions();
        if (currentEmptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating item");
        }
        Position itemPosition = currentEmptyPositions.get(random.nextInt(currentEmptyPositions.size()));
        items.add(itemFactory.createRandom(itemPosition, random));
    }

    private void applyItems() {
        for (Item item : items) {
            if (hero.getPosition().equals(item.getPosition())) {
                hero.addItem(item);
                items.remove(item);
                break;
            }
        }
    }

    private void fightWithMobs() {
        List<Mob> toRemoveMobs = new ArrayList<>();
        for (Mob mob : mobs) {
            if (hero.getPosition().equals(mob.getPosition())) {
                hero.attack(mob);
                mob.attack(hero);
                if (!mob.isAlive()) {
                    toRemoveMobs.add(mob);
                }
            }
        }
        for (Mob mob : toRemoveMobs) {
            mobs.remove(mob);
        }
    }

    private List<Position> getCurrentEmptyPositions() {
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
            for (Item item : items) {
                if (position.equals(item.getPosition())) {
                    currentEmpty = false;
                }
            }
            if (currentEmpty) {
                currentEmptyPositions.add(position);
            }
        }
        return currentEmptyPositions;
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
