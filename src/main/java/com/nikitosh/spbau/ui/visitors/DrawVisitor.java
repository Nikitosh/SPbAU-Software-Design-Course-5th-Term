package com.nikitosh.spbau.ui.visitors;

import asciiPanel.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.world.*;

public class DrawVisitor implements Visitor {
    private static final char EMPTY_CELL = '.';
    private static final char WALL       = '#';
    private static final char HERO       = '@';
    private static final char ORC        = 'O';
    private static final char TROLL      = 'T';

    private int height;
    private int width;
    private char initialCells[][];
    private char cells[][];

    public DrawVisitor(WorldMap map) {
        height = map.getHeight();
        width = map.getWidth();
        initialCells = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                initialCells[y][x] = EMPTY_CELL;
                if (!map.isEmptyCell(x, y)) {
                    initialCells[y][x] = WALL;
                }
            }
        }
        cells = new char[height][width];
        clear();
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = initialCells[i][j];
            }
        }
    }

    @Override
    public void visit(Hero hero) {
        draw(hero.getPosition(), HERO);
    }

    @Override
    public void visit(Orc orc) {
        draw(orc.getPosition(), ORC);
    }

    @Override
    public void visit(Troll troll) {
        draw(troll.getPosition(), TROLL);
    }

    public void draw(AsciiPanel panel) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                panel.write(cells[y][x], x, y);
            }
        }
    }

    private void draw(Position position, char symbol) {
        cells[position.getY()][position.getX()] = symbol;
    }
}
