package com.nikitosh.spbau.ui.visitors;

import asciiPanel.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;

import java.awt.*;

/**
 * Visitor which draw world's map.
 */

public class DrawVisitor implements Visitor {
    private static final char EMPTY_CELL_SYMBOL = '.';
    private static final char WALL_SYMBOL       = '#';
    private static final char HERO_SYMBOL       = '@';
    private static final char ORC_SYMBOL        = 'O';
    private static final char TROLL_SYMBOL      = 'T';
    private static final char KIT_SYMBOL        = '+';
    private static final char SHIELD_SYMBOL     = '*';

    private static final Color INITIAL_COLOR = Color.GRAY;
    private static final Color HERO_COLOR    = Color.ORANGE;
    private static final Color ORC_COLOR     = Color.GREEN;
    private static final Color TROLL_COLOR   = new Color(0, 128, 0);;
    private static final Color KIT_COLOR     = Color.RED;
    private static final Color SHIELD_COLOR  = Color.CYAN;

    private int height;
    private int width;
    private char[][] initialCells;
    private char[][] cells;
    private Color[][] initialColors;
    private Color[][] colors;

    public DrawVisitor(WorldMap map) {
        height = map.getHeight();
        width = map.getWidth();
        initialCells = new char[height][width];
        initialColors = new Color[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                initialCells[y][x] = EMPTY_CELL_SYMBOL;
                if (!map.isEmptyCell(x, y)) {
                    initialCells[y][x] = WALL_SYMBOL;
                }
                initialColors[y][x] = INITIAL_COLOR;
            }
        }
        cells = new char[height][width];
        colors = new Color[height][width];
        clear();
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = initialCells[i][j];
                colors[i][j] = initialColors[i][j];
            }
        }
    }

    @Override
    public void visit(Hero hero) {
        draw(hero.getPosition(), HERO_SYMBOL, HERO_COLOR);
    }

    @Override
    public void visit(Orc orc) {
        draw(orc.getPosition(), ORC_SYMBOL, ORC_COLOR);
    }

    @Override
    public void visit(Troll troll) {
        draw(troll.getPosition(), TROLL_SYMBOL, TROLL_COLOR);
    }

    @Override
    public void visit(Kit kit) {
        draw(kit.getPosition(), KIT_SYMBOL, KIT_COLOR);
    }

    @Override
    public void visit(Shield shield) {
        draw(shield.getPosition(), SHIELD_SYMBOL, SHIELD_COLOR);
    }

    public void draw(AsciiPanel panel) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                panel.write(cells[y][x], x, y, colors[y][x]);
            }
        }
    }

    private void draw(Position position, char symbol, Color color) {
        cells[position.getY()][position.getX()] = symbol;
        colors[position.getY()][position.getX()] = color;
    }
}
