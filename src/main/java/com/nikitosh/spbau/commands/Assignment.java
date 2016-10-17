package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.util.*;

/**
 * Implements <i> assignment </i> command.
 */

public class Assignment implements Command {
    /**
     * Assigns variable given in first argument to value given in second argument.
     *
     * @param  args list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return empty InputStream.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        if (args.size() != 2) {
            throw new SyntaxErrorException("assignment", "wrong number of arguments: expected 2 but found "
                    + args.size());
        }
        environment.setValue(args.get(0), args.get(1));
        return Utilities.getEmptyInputStream();
    }
}
