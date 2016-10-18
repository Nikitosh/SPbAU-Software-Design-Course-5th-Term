package com.nikitosh.spbau;

/**
 * Exception for showing that command line has some syntax errors.
 */

public class SyntaxErrorException extends RuntimeException {
    private String command;
    private String message;

    public SyntaxErrorException(String command, String message) {
        this.command = command;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "An error occured during processing command " + command + ": " + message;
    }
}
