package com.nikitosh.spbau.ui;

import asciiPanel.*;
import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;
import com.nikitosh.spbau.ui.visitors.*;

import javax.swing.*;

public class GameFrame extends JFrame {
    private static final String FRAME_NAME = "Roguelike";
    private static final int MIN_HEIGHT = 10;
    private static final int WIDTH = 64;

    private AsciiPanel panel;
    private DrawVisitor drawVisitor;

    public GameFrame(Game game) {
        super(FRAME_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        World world = game.getWorld();
        panel = new AsciiPanel(WIDTH, Math.max(world.getWorldMap().getHeight(), MIN_HEIGHT));
        game.setOnRender(this::render);
        add(panel);
        pack();
        //setResizable(false);
        setLocationRelativeTo(null); //set JFrame to appear in center

        addKeyListener(game.getPlayerStrategy());

        drawVisitor = new DrawVisitor(world.getWorldMap());
        render(world);
    }

    private void render(World world) {
        drawVisitor.clear();
        for (Creature creature : world.getAllCreatures()) {
            creature.accept(drawVisitor);
        }

        drawVisitor.draw(panel);
        panel.repaint();
    }
}
