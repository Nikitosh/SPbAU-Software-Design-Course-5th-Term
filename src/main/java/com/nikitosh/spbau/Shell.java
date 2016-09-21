package com.nikitosh.spbau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    private static final String EXIT = "exit";

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Shell shell = new Shell();
        Environment environment = new Environment();
        while (true) {
            try {
                String commandLine = reader.readLine();
                if (commandLine.equals(EXIT)) {
                    break;
                }
                try {
                    shell.process(commandLine, environment);
                } catch (SyntaxErrorException exception) {
                    System.err.println(exception.getMessage());
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void process(String commandLine, Environment environment) throws SyntaxErrorException {

    }
}
