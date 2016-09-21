package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public class Echo implements Command {
    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment) throws SyntaxErrorException {
        PipedOutputStream outputStream = new PipedOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String joinedString = String.join(" ", args) + "\n";
        try {
            dataOutputStream.writeUTF(joinedString);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return outputStream;
    }
}
