package com.nikitosh.spbau.model;

import com.nikitosh.spbau.model.strategies.*;
import com.nikitosh.spbau.model.strategies.inputlisteners.*;
import com.nikitosh.spbau.model.world.*;

import java.io.*;
import java.util.function.*;

public class Game {
    private World world;
    private KeyboardInputListener playerStrategy = new KeyboardInputListener();
    private Strategy mobsStrategy = new RandomStrategy();

    public Consumer<World> onRender = (world) -> {};

    public Game() throws FileNotFoundException{
        world = new World(new WorldMap(new File("src/main/resources/map.txt")));
    }

    public void run() {
        while (true) {
            world.makeTurn(playerStrategy, mobsStrategy);
            onRender.accept(world);
        }
    }

    public void setOnRender(Consumer<World> onRender) {
        this.onRender = onRender;
    }

    public World getWorld() {
        return world;
    }

    public KeyboardInputListener getPlayerStrategy() {
        return playerStrategy;
    }
}
