package com.nikitosh.spbau;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private static final char SPACE_SYMBOL = ' ';
    private static final char PIPE_SYMBOL = '|';
    private static final char SINGLE_QUOTE_SYMBOL = '\'';
    private static final char DOUBLE_QUOTE_SYMBOL = '"';
    private static final char SUBSTITUTION_SYMBOL = '$';
    private static final char ASSIGNMENT_SYMBOL = '=';

    private static final String SPLITTERS = " |'\"";

    public List<Lexeme> parse(String commandLine) throws SyntaxErrorException {
        int length = commandLine.length();
        List<Lexeme> lexemes = new ArrayList<>();
        String currentLexeme = "";
        boolean isAssignment = false, isSubstitution = false;
        for (int i = 0; i < length;) {
            char symbol = commandLine.charAt(i);
            if (SPLITTERS.contains(String.valueOf(symbol)) && !currentLexeme.isEmpty()) {
                lexemes.add(getLexeme(currentLexeme, isAssignment, isSubstitution));
                currentLexeme = "";
            }
            if (symbol == SPACE_SYMBOL) {
                i++;
                continue;
            }
            if (symbol == PIPE_SYMBOL) {
                lexemes.add(new Lexeme(Lexeme.getType(commandLine.charAt(i)), String.valueOf(symbol)));
                i++;
                continue;
            }
            if (symbol == SINGLE_QUOTE_SYMBOL || symbol == DOUBLE_QUOTE_SYMBOL) {
                int quotesEndIndex = 0;
                boolean foundQuotePair = false;
                for (int j = i + 1; j < length; j++) {
                    if (commandLine.charAt(j) == symbol) {
                        quotesEndIndex = j;
                        foundQuotePair = true;
                        break;
                    }
                }
                if (!foundQuotePair) {
                    String commandName = "";
                    if (symbol == SINGLE_QUOTE_SYMBOL) {
                        commandName = "single quotes";
                    } else {
                        commandName = "double quotes";
                    }
                    throw new SyntaxErrorException(commandName, "no paired quote found");
                }
                lexemes.add(new Lexeme(Lexeme.getType(symbol), commandLine.substring(i, quotesEndIndex + 1)));
                i = quotesEndIndex + 1;
                continue;
            }
            if (symbol == SUBSTITUTION_SYMBOL) {
                isSubstitution = true;
            }
            if (symbol == ASSIGNMENT_SYMBOL) {
                isAssignment = true;
            }
            currentLexeme += symbol;
            i++;
        }
        if (!currentLexeme.isEmpty()) {
            lexemes.add(getLexeme(currentLexeme, isAssignment, isSubstitution));
        }
        return lexemes;
    }

    private Lexeme getLexeme(String content, boolean isAssignment, boolean isSubstitution) {
        if (isSubstitution) {
            return new Lexeme(Lexeme.Type.SUBSTITUTION, content);
        }
        if (isAssignment) {
            return new Lexeme(Lexeme.Type.ASSIGNMENT, content);
        }
        return new Lexeme(Lexeme.Type.TEXT, content);
    }
}
