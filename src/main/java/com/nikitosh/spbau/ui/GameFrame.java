package com.nikitosh.spbau.ui;

import asciiPanel.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

import javax.swing.*;
import java.util.*;

public class GameFrame extends JFrame {
    private static final String FRAME_NAME = "Roguelike";

    private static final String INVENTORY = "Inventory";
    private static final String NUMBER = "# ";
    private static final String NAME = "Name      ";
    private static final String HEALTH = "HP ";
    private static final String ATTACK = "Att ";
    private static final String DEFENSE = "Def ";
    private static final String ON = "On";
    private static final int MIN_HEIGHT = 12;
    private static final int ATTRIBUTES_COLUMN_WIDTH = 15;
    private static final int NUMBER_COLUMN_WIDTH = NUMBER.length();
    private static final int NAME_COLUMN_WIDTH = NAME.length();
    private static final int HEALTH_COLUMN_WIDTH = HEALTH.length();
    private static final int ATTACK_COLUMN_WIDTH = ATTACK.length();
    private static final int DEFENSE_COLUMN_WIDTH = DEFENSE.length();
    private static final int ON_COLUMN_WIDTH = ON.length();

    private static final int NAME_COLUMN_OFFSET = NUMBER_COLUMN_WIDTH;
    private static final int HEALTH_COLUMN_OFFSET = NAME_COLUMN_OFFSET + NAME_COLUMN_WIDTH;
    private static final int ATTACK_COLUMN_OFFSET = HEALTH_COLUMN_OFFSET + HEALTH_COLUMN_WIDTH;
    private static final int DEFENSE_COLUMN_OFFSET = ATTACK_COLUMN_OFFSET + ATTACK_COLUMN_WIDTH;
    private static final int ON_COLUMN_OFFSET = DEFENSE_COLUMN_OFFSET + DEFENSE_COLUMN_WIDTH;
    private static final int INVENTORY_COLUMN_WIDTH = ON_COLUMN_OFFSET + ON_COLUMN_WIDTH + 1;


    private static final char ITEM_ON = '+';
    private static final char ITEM_OFF = '-';

    private AsciiPanel panel;
    private DrawVisitor drawVisitor;
    private int mapWidth;

    public GameFrame(Game game) {
        super(FRAME_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        World world = game.getWorld();
        mapWidth = world.getWorldMap().getWidth();

        panel = new AsciiPanel(mapWidth + ATTRIBUTES_COLUMN_WIDTH + INVENTORY_COLUMN_WIDTH,
                Math.max(world.getWorldMap().getHeight(), MIN_HEIGHT));
        game.setOnRender(this::render);
        add(panel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null); //set JFrame to appear in center


        addKeyListener(game.getPlayerStrategy());

        drawVisitor = new DrawVisitor(world.getWorldMap());
        render(world);
    }

    private void render(World world) {
        panel.clear();
        drawVisitor.clear();
        for (GameObject gameObject : world.getGameObjects()) {
            gameObject.accept(drawVisitor);
        }

        drawVisitor.draw(panel);
        drawAttributes(world.getHero());
        drawInventory(world.getHero());
        panel.repaint();
    }

    private void drawAttributes(Hero hero) {
        panel.setCursorPosition(mapWidth + 1, 0);
        panel.write(" HP: " + hero.getAttributes().getHealth());
        panel.setCursorPosition(mapWidth + 1, 1);
        panel.write("Att: " + hero.getAttributes().getAttack());
        panel.setCursorPosition(mapWidth + 1, 2);
        panel.write("Def: " + hero.getAttributes().getDefense());
    }

    private void drawInventory(Hero hero) {
        int x = mapWidth + ATTRIBUTES_COLUMN_WIDTH;
        panel.setCursorPosition(x, 0);
        panel.write(INVENTORY + ":");
        List<Item> items = hero.getInventory();
        panel.setCursorPosition(x, 2);
        panel.write(NUMBER + NAME + HEALTH + ATTACK + DEFENSE + ON);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            panel.setCursorPosition(x, i + 3);
            panel.write(String.valueOf(i + 1));
            panel.setCursorPosition(x + NAME_COLUMN_OFFSET, i + 3);
            panel.write(item.getName());
            panel.setCursorPosition(x + HEALTH_COLUMN_OFFSET, i + 3);
            panel.write(String.valueOf(item.getAttributes().getHealth()));
            panel.setCursorPosition(x + ATTACK_COLUMN_OFFSET, i + 3);
            panel.write(String.valueOf(item.getAttributes().getAttack()));
            panel.setCursorPosition(x + DEFENSE_COLUMN_OFFSET, i + 3);
            panel.write(String.valueOf(item.getAttributes().getDefense()));
            panel.setCursorPosition(x + ON_COLUMN_OFFSET, i + 3);
            panel.write(item.isActivated() ? ITEM_ON : ITEM_OFF);
        }
    }
}
