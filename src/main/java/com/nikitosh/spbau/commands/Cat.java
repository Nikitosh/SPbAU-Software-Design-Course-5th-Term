package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Cat implements Command {
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
