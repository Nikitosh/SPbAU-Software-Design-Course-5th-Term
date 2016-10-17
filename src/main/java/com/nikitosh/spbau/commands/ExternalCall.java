package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;

import java.io.*;
import java.util.*;

/**
 * Implements <i> external call </i> command.
 * If no standard command suits current request, Shell tries to handle it like it would be external call.
 */

public class ExternalCall implements Command {
    /**
     * Executes some external program if no suitable command among available found.
     * Uses first argument as program name and other arguments as program's arguments, reads from inputStream.
     *
     * @param  args list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return result of execution of external program.
     */
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
