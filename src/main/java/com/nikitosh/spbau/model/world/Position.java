package com.nikitosh.spbau.model.world;

public class  Position {
    private static final int P = 239017;

    private int[] xShift = {0, 1, 0, -1, 0};
    private int[] yShift = {-1, 0, 1, 0, 0};

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(WorldMap map, Movement movement) {
        int newX = x + xShift[movement.ordinal()];
        int newY = y + yShift[movement.ordinal()];
        try {
            if (map.isEmptyCell(newX, newY)) {
                x = newX;
                y = newY;
            }
        } catch (IndexOutOfBoundsException ignored) {}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return x * P + y;
    }
}
