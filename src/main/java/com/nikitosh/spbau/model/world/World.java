package com.nikitosh.spbau.model.world;

import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.strategies.*;

import java.util.*;

public class World {
    /**
     * Number of mobs on the map at the start.
     */
    private static final int MOBS_AMOUNT = 20;
    /**
     * Number of items on the map at the start.
     */
    private static final int ITEMS_AMOUNT = 3;

    private WorldMap map;
    private Hero hero;
    private List<Mob> mobs = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Position> emptyPositions;
    private Random random = new Random();
    private MobFactory mobFactory = new MobFactory();
    private ItemFactory itemFactory = new ItemFactory();

    /**
     * Creates new World according to given map.
     * Generates hero, mobs and items.
     *
     * @param map world's map.
     */
    public World(WorldMap map) {
        this.map = map;
        getEmptyPositions();
        hero = generateHero();
        for (int i = 0; i < MOBS_AMOUNT; i++) {
            mobs.add(generateMob());
        }
        for (int i = 0; i < ITEMS_AMOUNT; i++) {
            items.add(generateItem());
        }
    }

    /**
     * Makes one turn of game.
     *
     * @param playerStrategy strategy which hero is using.
     * @param mobsStrategy strategy which mobs are using.
     */
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

    /**
     * Returns all game objects.
     *
     * @return list of all game objects in the world at the moment, including hero, mobs and items.
     */
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

    /**
     * Generates hero on some empty cell.
     */
    private Hero generateHero() {
        if (emptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating hero");
        }
        Position heroPosition = emptyPositions.get(random.nextInt(emptyPositions.size()));
        return new Hero(heroPosition);
    }

    /**
     * Generates random mob on some empty cell.
     */
    private Mob generateMob() {
        List<Position> currentEmptyPositions = getCurrentEmptyPositions();
        if (currentEmptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating mob");
        }
        Position mobPosition = currentEmptyPositions.get(random.nextInt(currentEmptyPositions.size()));
        return mobFactory.createRandom(mobPosition, random);
    }

    /**
     * Generates random item on some empty cell.
     */
    private Item generateItem() {
        List<Position> currentEmptyPositions = getCurrentEmptyPositions();
        if (currentEmptyPositions.isEmpty()) {
            throw new RuntimeException("No available cell for generating item");
        }
        Position itemPosition = currentEmptyPositions.get(random.nextInt(currentEmptyPositions.size()));
        return itemFactory.createRandom(itemPosition, random);
    }

    /**
     * Performs applying of items which are placed on same cell with hero.
     */
    private void applyItems() {
        for (Item item : items) {
            if (hero.getPosition().equals(item.getPosition())) {
                hero.addItem(item);
                items.remove(item);
                break;
            }
        }
    }

    /**
     * Performs fights of hero with all mobs which are placed on same cell with him.
     */
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

    /**
     * Returns empty cells at the moment.
     *
     * @return list of empty positions on world's map (taking into account cells occupied by creatures and items).
     */
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

    /**
     * Returns empty cells.
     *
     * @return list of initial empty positions on world's map.
     */
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
