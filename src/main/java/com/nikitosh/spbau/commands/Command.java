package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public interface Command {
    PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment)
            throws SyntaxErrorException;
}
