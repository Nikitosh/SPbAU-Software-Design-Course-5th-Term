package com.nikitosh.spbau.model.world;

import java.io.*;
import java.util.*;

import static com.nikitosh.spbau.model.world.WorldMap.CellType.EMPTY;
import static com.nikitosh.spbau.model.world.WorldMap.CellType.WALL;


public class WorldMap {
    private static final char EMPTY_SYMBOL = '.';
    private static final char WALL_SYMBOL = '#';

    private int height;
    private int width;
    private CellType[][] cells;

    /**
     * Type of map's cell.
     */
    public enum CellType {
        EMPTY,
        WALL
    };

    /**
     * Reads new map from file and creates the map according to conten.
     *
     * @param file file to read the map from.
     * @throws FileNotFoundException
     */
    public WorldMap(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        height = scanner.nextInt();
        width = scanner.nextInt();
        cells = new CellType[height][width];
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            String s = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                if (s.charAt(j) == EMPTY_SYMBOL) {
                    cells[i][j] = EMPTY;
                } else if (s.charAt(j) == WALL_SYMBOL) {
                    cells[i][j] = WALL;
                } else {
                    throw new RuntimeException("Invalid symbol in map occured");
                }
            }
        }
    }

    /**
     * Checks whether given cell is empty.
     *
     * @param x x-coordinate of cell.
     * @param y y-coordinate of cell.
     * @return whether cell is empty.
     */
    public boolean isEmptyCell(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && cells[y][x] == EMPTY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
