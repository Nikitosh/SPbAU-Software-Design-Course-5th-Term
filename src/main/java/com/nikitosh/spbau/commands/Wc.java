package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

public class Wc implements Command {
    private static class Statistics {
        private int newlinesNumber = 0;
        private int wordsNumber = 0;
        private int bytesNumber = 0;

        public Statistics() {
        }

        public Statistics(int newlinesNumber, int wordsNumber, int bytesNumber) {
            this.newlinesNumber = newlinesNumber;
            this.wordsNumber = wordsNumber;
            this.bytesNumber = bytesNumber;
        }

        public void add(Statistics statistics) {
            newlinesNumber += statistics.newlinesNumber;
            wordsNumber += statistics.wordsNumber;
            bytesNumber += statistics.wordsNumber;
        }

        public void print(PrintWriter writer) {
            writer.print(newlinesNumber + " " + wordsNumber + " " + bytesNumber + "\n");
        }
    }

    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment)
            throws SyntaxErrorException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        if (args.isEmpty()) {
            try {
                getStringStatistics(IOUtils.toString(inputStream)).print(writer);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            Statistics totalStatistics = new Statistics();
            for (String arg : args) {
                try {
                    Statistics currentStatistics = getStringStatistics(new String(Files.readAllBytes(Paths.get(arg)),
                            StandardCharsets.UTF_8));
                    totalStatistics.add(currentStatistics);
                    currentStatistics.print(writer);
                } catch (InvalidPathException exception) {
                    throw new SyntaxErrorException("wc", arg + ": no such file");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (args.size() > 1) {
                totalStatistics.print(writer);
            }
        }
        return outputStream;
    }

    private Statistics getStringStatistics(String content) {
        int newlinesNumber = StringUtils.countMatches(content, "\n");
        int wordsNumber = content.trim().split("\\s+").length;
        int bytesNumber = content.length();
        return new Statistics(newlinesNumber, wordsNumber, bytesNumber);
    }
}
