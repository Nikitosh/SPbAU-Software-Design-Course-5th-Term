package com.nikitosh.spbau;

import java.util.*;

/**
 * Objects, which Lexer produces parsing given string.
 * It's described by two it's type and content.
 */

public class Lexeme {
    private static final int P = 1087;

    /**
     * Lexeme's available types.
     */
    public enum Type {
        /**
         * text lexeme.
         */
        TEXT,
        /**
         * pipe symbol.
         */
        PIPE,
        /**
         * text surrounded by single quotes.
         */
        SINGLE_QUOTE,
        /**
         * text surrounded by double quotes.
         */
        DOUBLE_QUOTE,
        /**
         * text with variable which should be substituted.
         */
        SUBSTITUTION,
        /**
         * text like variableName=value.
         */
        ASSIGNMENT
    }

    private static final Map<Character, Type> TYPES = new HashMap<Character, Type>() {
        {
            put('|',  Type.PIPE);
            put('\'', Type.SINGLE_QUOTE);
            put('"',  Type.DOUBLE_QUOTE);
        }
    };

    private Type type;
    private String content;

    Lexeme(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    Type getType() {
        return type;
    }

    String getContent() {
        return content;
    }

    public static Type getType(char symbol) {
        return TYPES.get(symbol);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Lexeme) {
            Lexeme lexeme = (Lexeme) other;
            return type.equals(lexeme.type) && content.equals(lexeme.content);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return content.hashCode() * P + type.hashCode();
    }
}
