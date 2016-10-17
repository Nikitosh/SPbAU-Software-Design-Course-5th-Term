package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Implements <i> pwd </i> command.
 */

public class Pwd implements Command {
    /**
     * Prints path to current directory.
     *
     * @param  args list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return path to current directory.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        return new ByteArrayInputStream((Paths.get(".").toAbsolutePath().normalize().toString() + "\n").getBytes());
    }
}
