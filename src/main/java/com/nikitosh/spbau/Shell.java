package com.nikitosh.spbau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Shell {
    private static final String EXIT = "exit";

    private Environment environment = new Environment();
    private Lexer lexer = new Lexer();
    private Parser parser = new Parser();

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Shell shell = new Shell();
        while (true) {
            try {
                String commandLine = reader.readLine();
                if (commandLine.equals(EXIT)) {
                    break;
                }
                try {
                    shell.process(commandLine);
                } catch (SyntaxErrorException exception) {
                    System.err.println(exception.getMessage());
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void process(String commandLine) throws SyntaxErrorException {
        List<Lexeme> lexemes = lexer.parse(commandLine);
        lexemes = lexer.substitute(lexemes, environment);
        parser.execute(lexemes, environment);
    }
}
