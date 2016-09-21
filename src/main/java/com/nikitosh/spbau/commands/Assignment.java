package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public class Assignment implements Command {
    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment)
            throws SyntaxErrorException {
        if (args.size() != 2) {
            throw new SyntaxErrorException("assignment", "wrong number of arguments: expected 2 but found " +
                    args.size());
        }
        environment.setValue(args.get(0), args.get(1));
        return new PipedOutputStream();
    }
}
