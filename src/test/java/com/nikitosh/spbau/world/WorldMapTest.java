package com.nikitosh.spbau.world;

import com.nikitosh.spbau.model.world.*;
import org.junit.*;
import org.junit.rules.*;

import java.io.*;

import static junitx.framework.Assert.*;

//CheckStyle:OFF: MagicNumber

public class WorldMapTest {
    private static final int HEIGHT = 5;
    private static final int WIDTH = 6;
    private static final String[] CELLS = {
        "...#.#",
        "##....",
        "...##.",
        ".#.#..",
        ".....#"
    };

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testParsing() throws IOException {
        File mapFile = testFolder.newFile("map.txt");
        PrintWriter printWriter = new PrintWriter(mapFile);
        printWriter.print(HEIGHT + " " + WIDTH + "\n");
        for (int i = 0; i < HEIGHT; i++) {
            printWriter.println(CELLS[i]);
        }
        printWriter.flush();
        WorldMap map = new WorldMap(mapFile);

        assertEquals(HEIGHT, map.getHeight());
        assertEquals(WIDTH, map.getWidth());
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                assertEquals((CELLS[y].charAt(x) == '.'), map.isCellEmpty(x, y));
            }
        }
    }
}

//CheckStyle:ON: MagicNumber
