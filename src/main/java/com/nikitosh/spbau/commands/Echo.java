package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.*;
import java.util.List;

public class Echo implements Command {
    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment)
            throws SyntaxErrorException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PrintWriter writer = new PrintWriter(new BufferedOutputStream(outputStream));
        writer.print(String.join(" ", args) + "\n");
        return outputStream;
    }
}
