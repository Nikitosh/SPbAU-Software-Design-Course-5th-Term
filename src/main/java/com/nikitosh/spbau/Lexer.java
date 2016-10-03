package com.nikitosh.spbau;

import java.util.List;

public interface Lexer {
    List<Lexeme> parse(String commandLine) throws SyntaxErrorException;
    List<Lexeme> substitute(List<Lexeme> lexemes, Environment environment);
}
