package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public class ExternalCall implements Command {

    @Override
    public PipedOutputStream execute(List<String> args, PipedInputStream inputStream, Environment environment) throws SyntaxErrorException {
        PipedOutputStream outputStream = new PipedOutputStream();
        try {
            Process process = new ProcessBuilder(args).start();
            IOUtils.copy(inputStream, process.getOutputStream());
            try {
                process.waitFor();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            IOUtils.copy(process.getInputStream(), outputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return outputStream;
    }
}
