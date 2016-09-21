package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

public class Cat implements Command {
    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment)
            throws SyntaxErrorException {
        PipedOutputStream outputStream = new PipedOutputStream();

        if (args.isEmpty()) {
            try {
                outputStream.connect(inputStream);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                try {
                    stringBuilder.append(new String(Files.readAllBytes(Paths.get(arg)), StandardCharsets.UTF_8));
                } catch (InvalidPathException exception) {
                    throw new SyntaxErrorException("cat", arg + ": no such file");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            PrintWriter writer = new PrintWriter(new BufferedOutputStream(outputStream));
            writer.print(stringBuilder.toString());
        }
        return outputStream;
    }
}
