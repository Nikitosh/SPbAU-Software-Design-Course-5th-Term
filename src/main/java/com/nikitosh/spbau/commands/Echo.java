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
        for (int i = 0; i < args.size(); i++) {
            try {
                dataOutputStream.writeUTF(args.get(i));
                if (i != args.size() - 1) {
                    dataOutputStream.writeUTF(" ");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            dataOutputStream.writeUTF("\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return outputStream;
    }
}
