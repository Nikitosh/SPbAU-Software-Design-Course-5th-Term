package com.nikitosh.spbau;

import java.util.*;

public interface Lexer {
    List<Lexeme> parse(String commandLine) throws SyntaxErrorException;
    List<Lexeme> substitute(List<Lexeme> lexemes, Environment environment);
}
