package com.nikitosh.spbau;

import java.util.*;

/**
 * Interface, which is used for parsing given string into lexemes.
 */

public interface Lexer {
    /**
     * Parses given string into list of lexemes.
     *
     * @param  commandLine String to parse.
     *
     * @return list of lexemes which were produced during parsing.
     */
    List<Lexeme> parse(String commandLine) throws SyntaxErrorException;

    /**
     * Substitute variables' values for each lexeme in given list.
     *
     * @param  lexemes list of lexemes in which Lexer will try to substitute variables.
     * @param  environment environment which is used for getting variables' values.
     *
     * @return list of substituted lexemes.
     */
    List<Lexeme> substitute(List<Lexeme> lexemes, Environment environment);
}
