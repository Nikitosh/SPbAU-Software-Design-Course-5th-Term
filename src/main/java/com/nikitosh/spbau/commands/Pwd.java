package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

public class Pwd implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        return new ByteArrayInputStream((Paths.get(".").toAbsolutePath().normalize().toString() + "\n").getBytes());
    }
}
