package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExternalCall implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        Process process = new ProcessBuilder(args).start();
        IOUtils.copy(inputStream, process.getOutputStream());
        try {
            process.waitFor();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        return process.getInputStream();
    }
}
