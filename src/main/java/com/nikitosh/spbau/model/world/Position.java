package com.nikitosh.spbau.model.world;

public class Position {
    private int[] xShift = {0, 1, 0, -1};
    private int[] yShift = {-1, 0, 1, 0};

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public enum Movement {
        MOVEMENT_UP,
        MOVEMENT_RIGHT,
        MOVEMENT_DOWN,
        MOVEMENT_LEFT
    };

    public void move(Movement movement) {
        x += xShift[movement.ordinal()];
        y += yShift[movement.ordinal()];
    }
}
