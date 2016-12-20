package com.nikitosh.spbau;

import com.nikitosh.spbau.model.*;
import com.nikitosh.spbau.ui.*;

import java.io.*;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        try {
            Game game = new Game();
            GameFrame frame = new GameFrame(game);
            frame.setVisible(true);
            game.run();
        } catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
