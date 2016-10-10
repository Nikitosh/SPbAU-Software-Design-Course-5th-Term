package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.util.*;

/**
 * Implements <i> echo </i> command.
 */

public class Echo implements Command {
    /**
     *
     * @param  args        list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return             content of args, splitted by whitespace.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        return new ByteArrayInputStream((String.join(" ", args) + "\n").getBytes());
    }
}
