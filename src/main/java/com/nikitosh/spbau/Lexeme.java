package com.nikitosh.spbau;

import java.util.*;

public class Lexeme {
    public enum Type {
        TEXT,
        PIPE,
        SINGLE_QUOTE,
        DOUBLE_QUOTE,
        SUBSTITUTION,
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
}
