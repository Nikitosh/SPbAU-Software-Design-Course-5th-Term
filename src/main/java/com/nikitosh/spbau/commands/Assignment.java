package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import com.nikitosh.spbau.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Assignment implements Command {
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
