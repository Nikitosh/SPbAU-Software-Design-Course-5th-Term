package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/**
 * Implements <i> wc </i> command.
 */

public class Wc implements Command {

    /**
     * Helper class, which is used for counting number of new lines, words and bytes of file's content.
     */
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

        static Statistics getStringStatistics(String content) {
            int newlinesNumber = StringUtils.countMatches(content, "\n");
            int wordsNumber = content.trim().split("\\s+").length;
            int bytesNumber = content.length();
            return new Statistics(newlinesNumber, wordsNumber, bytesNumber);
        }
    }

    /**
     * Prints number of new lines, words ans bytes for each file given in arguments,
     * or for content of inputStream if no arguments provided.
     *
     * @param  args list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return three numbers for each file: numbers of new lines, words and bytes.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        StringBuilder executionResult = new StringBuilder();
        if (args.isEmpty()) {
            executionResult.append(Statistics.getStringStatistics(IOUtils.toString(inputStream)).toString());
        } else {
            Statistics totalStatistics = new Statistics();
            for (String arg : args) {
                try {
                    Statistics currentStatistics = Statistics.getStringStatistics(new String(
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
}
