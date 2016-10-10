package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/**
 * Implements <i> cat </i> command.
 */

public class Cat implements Command {
    /**
     *
     * @param  args        list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return             content of files, given in args.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        if (args.isEmpty()) {
            return inputStream;
        } else {
            StringBuilder executionResult = new StringBuilder();
            for (String arg : args) {
                try {
                    executionResult.append(new String(Files.readAllBytes(Paths.get(arg)), StandardCharsets.UTF_8));
                } catch (InvalidPathException | NoSuchFileException exception) {
                    throw new SyntaxErrorException("cat", arg + ": no such file");
                }
            }
            return new ByteArrayInputStream(executionResult.toString().getBytes());
        }
    }
}
