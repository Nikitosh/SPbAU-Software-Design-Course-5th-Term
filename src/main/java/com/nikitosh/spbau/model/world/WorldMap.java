package com.nikitosh.spbau.model.world;

import java.io.*;
import java.util.*;

import static com.nikitosh.spbau.model.world.WorldMap.CellType.EMPTY;
import static com.nikitosh.spbau.model.world.WorldMap.CellType.WALL;


public class WorldMap {
    private int height;
    private int width;
    private CellType[][] cells;

    public enum CellType {
        EMPTY,
        WALL
    };

    public WorldMap(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        height = scanner.nextInt();
        width = scanner.nextInt();
        cells = new CellType[height][width];
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            String s = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                if (s.charAt(j) == '.') {
                    cells[i][j] = EMPTY;
                } else if (s.charAt(j) == '#') {
                    cells[i][j] = WALL;
                } else {
                    throw new RuntimeException("Invalid symbol in map occured");
                }
            }
        }
    }

    public boolean isEmptyCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }
        return cells[y][x] == EMPTY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
