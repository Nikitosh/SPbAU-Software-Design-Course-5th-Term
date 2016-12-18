package com.nikitosh.spbau.model;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.run();
        } catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
