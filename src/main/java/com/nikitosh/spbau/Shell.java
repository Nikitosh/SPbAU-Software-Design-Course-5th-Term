package com.nikitosh.spbau;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public class Shell {
    private static final String EXIT = "exit";
    private static final String NEW_LINE_SEPARATOR = "$";

    private EnvironmentImpl environment = new EnvironmentImpl();
    private Lexer lexer = new Lexer();
    private Parser parser = new Parser();

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Shell shell = new Shell();
        while (true) {
            System.out.print(NEW_LINE_SEPARATOR);
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
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void process(String commandLine) throws SyntaxErrorException {
        List<Lexeme> lexemes = lexer.parse(commandLine);
        lexemes = lexer.substitute(lexemes, environment);
        InputStream inputStream = parser.execute(lexemes, environment);
        PrintStream outputStream = System.out;
        try {
            outputStream.print(IOUtils.toString(inputStream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
