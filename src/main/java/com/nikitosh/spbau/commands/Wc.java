package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Wc implements Command {
    private static class Statistics {
        private int newlinesNumber = 0;
        private int wordsNumber = 0;
        private int bytesNumber = 0;

        Statistics() {
        }

        Statistics(int newlinesNumber, int wordsNumber, int bytesNumber) {
            this.newlinesNumber = newlinesNumber;
            this.wordsNumber = wordsNumber;
            this.bytesNumber = bytesNumber;
        }

        public void add(Statistics statistics) {
            newlinesNumber += statistics.newlinesNumber;
            wordsNumber += statistics.wordsNumber;
            bytesNumber += statistics.bytesNumber;
        }

        @Override
        public String toString() {
            return newlinesNumber + " " + wordsNumber + " " + bytesNumber + "\n";
        }
    }

    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        StringBuilder executionResult = new StringBuilder();
        if (args.isEmpty()) {
            executionResult.append(getStringStatistics(IOUtils.toString(inputStream)).toString());
        } else {
            Statistics totalStatistics = new Statistics();
            for (String arg : args) {
                try {
                    Statistics currentStatistics = getStringStatistics(new String(
                            Files.readAllBytes(Paths.get(arg)), StandardCharsets.UTF_8));
                    totalStatistics.add(currentStatistics);
                    executionResult.append(currentStatistics.toString());
                } catch (InvalidPathException | NoSuchFileException exception) {
                    throw new SyntaxErrorException("wc", arg + ": no such file");
                }
            }
            if (args.size() > 1) {
                executionResult.append(totalStatistics.toString());
            }
        }
        return new ByteArrayInputStream(executionResult.toString().getBytes());
    }

    private Statistics getStringStatistics(String content) {
        int newlinesNumber = StringUtils.countMatches(content, "\n");
        int wordsNumber = content.trim().split("\\s+").length;
        int bytesNumber = content.length();
        return new Statistics(newlinesNumber, wordsNumber, bytesNumber);
    }
}
