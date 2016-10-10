package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.util.*;

/**
 * Interface for all available Shell commands.
 */

public interface Command {
    /**
     *
     * @param  args        list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return             result of command execution as InputStream.
     */
    InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException;
}
