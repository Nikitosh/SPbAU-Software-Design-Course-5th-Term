package com.nikitosh.spbau.model.world;

/**
 * Class which shows a position on world's map.
 * Holds just two coordinates: x and y.
 */

public class  Position {
    private static final int P = 239017;

    /**
     * Shifts for X-axis in order of Movement enum.
     */
    private int[] xShift = {0, 1, 0, -1, 0};
    /**
     * Shifts for Y-axis in order of Movement enum.
     */
    private int[] yShift = {-1, 0, 1, 0, 0};

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Changes position according to given movement.
     *
     * @param map world's map.
     * @param movement movement to be performed.
     */
    public void move(WorldMap map, Movement movement) {
        int newX = x + xShift[movement.ordinal()];
        int newY = y + yShift[movement.ordinal()];
        if (map.isCellEmpty(newX, newY)) {
            x = newX;
            y = newY;
        }
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
