package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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

        if (args.size() == 0) {
            try {
                outputStream.connect(inputStream);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            for (String arg : args) {
                try {
                    dataOutputStream.writeUTF(new String(Files.readAllBytes(Paths.get(arg)), StandardCharsets.UTF_8));
                } catch (InvalidPathException exception) {
                    throw new SyntaxErrorException("cat", arg + ": no such file");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return outputStream;
    }
}
