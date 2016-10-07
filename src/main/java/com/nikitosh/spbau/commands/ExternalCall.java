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
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        if (inputStream == System.in) {
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        }
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException exception) {
            throw new SyntaxErrorException("external call", "command not found");
        }
        if (inputStream != System.in) {
            IOUtils.copy(inputStream, process.getOutputStream());
            process.getOutputStream().close();
        }
        try {
            process.waitFor();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        return process.getInputStream();
    }
}
