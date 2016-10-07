package com.nikitosh.spbau;

import java.util.*;

public class LexerImpl implements Lexer {
    private static final char SPACE_SYMBOL = ' ';
    private static final char PIPE_SYMBOL = '|';
    private static final char SINGLE_QUOTE_SYMBOL = '\'';
    private static final char DOUBLE_QUOTE_SYMBOL = '"';
    private static final char SUBSTITUTION_SYMBOL = '$';
    private static final char ASSIGNMENT_SYMBOL = '=';

    private static final String SPLITTERS = " |'\"";

    @Override
    public List<Lexeme> parse(String commandLine) throws SyntaxErrorException {
        int length = commandLine.length();
        List<Lexeme> lexemes = new ArrayList<>();
        String currentLexeme = "";
        boolean isAssignment = false;
        boolean isSubstitution = false;
        for (int i = 0; i < length;) {
            char symbol = commandLine.charAt(i);
            if (SPLITTERS.contains(String.valueOf(symbol))) {
                if (!currentLexeme.isEmpty()) {
                    lexemes.add(getLexeme(currentLexeme, isAssignment, isSubstitution));
                    currentLexeme = "";
                }
                isAssignment = false;
                isSubstitution = false;
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

    @Override
        public List<Lexeme> substitute(List<Lexeme> lexemes, Environment environment) {
        List<Lexeme> substitutedLexemes = new ArrayList<>();
        for (Lexeme lexeme : lexemes) {
            if (lexeme.getType() == Lexeme.Type.SINGLE_QUOTE) {
                String content = lexeme.getContent();
                substitutedLexemes.add(new Lexeme(Lexeme.Type.TEXT, content.substring(1, content.length() - 1)));
                continue;
            }
            if (lexeme.getType() == Lexeme.Type.DOUBLE_QUOTE) {
                String content = lexeme.getContent();
                content = content.substring(1, content.length() - 1);
                substitutedLexemes.add(new Lexeme(Lexeme.Type.TEXT, substitute(content, environment)));
                continue;
            }
            if (lexeme.getType() == Lexeme.Type.SUBSTITUTION) {
                substitutedLexemes.add(new Lexeme(Lexeme.Type.TEXT, substitute(lexeme.getContent(), environment)));
                continue;
            }
            substitutedLexemes.add(lexeme);
        }
        return substitutedLexemes;
    }

    private String substitute(String content, Environment environment) {
        int length = content.length();
        String result = "";
        for (int i = 0; i < length;) {
            if (content.charAt(i) != SUBSTITUTION_SYMBOL) {
                result += content.charAt(i++);
                continue;
            }
            int j = i + 1;
            String variable = "";
            while (j < length && Character.isLetterOrDigit(content.charAt(j))) {
                variable += content.charAt(j++);
            }
            if (!variable.isEmpty()) {
                result += environment.getValue(variable);
            } else {
                result += content.charAt(i);
            }
            i = j;
        }
        return result;
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
