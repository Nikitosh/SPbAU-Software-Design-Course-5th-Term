package com.nikitosh.spbau;

public class SyntaxErrorException extends Exception {
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
