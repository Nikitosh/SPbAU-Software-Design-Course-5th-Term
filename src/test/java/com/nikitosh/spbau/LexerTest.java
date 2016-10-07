package com.nikitosh.spbau;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LexerTest {

    @Test
    public void testParse() throws IOException {
        Lexer lexer = new LexerImpl();
        assertEquals(Arrays.asList(
                        new Lexeme(Lexeme.Type.ASSIGNMENT, "a=5"),
                        new Lexeme(Lexeme.Type.PIPE, "|"),
                        new Lexeme(Lexeme.Type.SUBSTITUTION, "ech$d"),
                        new Lexeme(Lexeme.Type.DOUBLE_QUOTE, "\"abc  de\""),
                        new Lexeme(Lexeme.Type.ASSIGNMENT, "s=2"),
                        new Lexeme(Lexeme.Type.PIPE, "|"),
                        new Lexeme(Lexeme.Type.SINGLE_QUOTE, "'cat'"),
                        new Lexeme(Lexeme.Type.PIPE, "|"),
                        new Lexeme(Lexeme.Type.SUBSTITUTION, "r=$a")
                ),
                lexer.parse(" a=5  |ech$d \"abc  de\"  s=2|    'cat'  |r=$a"));
    }

    @Test
    public void testSubstituteSingleQuotes() throws IOException {
        Environment environment = mock(Environment.class);
        when(environment.getValue("a")).thenReturn("123");
        when(environment.getValue("b")).thenReturn("456");
        when(environment.getValue("c")).thenReturn("");
        when(environment.getValue("aba")).thenReturn("");
        Lexer lexer = new LexerImpl();
        assertEquals(Arrays.asList(
                        new Lexeme(Lexeme.Type.TEXT, "$a text b $b$c$a"),
                        new Lexeme(Lexeme.Type.TEXT, "$aba"),
                        new Lexeme(Lexeme.Type.TEXT, "a b   a b a")
                ),
                lexer.substitute(Arrays.asList(
                        new Lexeme(Lexeme.Type.SINGLE_QUOTE, "'$a text b $b$c$a'"),
                        new Lexeme(Lexeme.Type.SINGLE_QUOTE, "'$aba'"),
                        new Lexeme(Lexeme.Type.SINGLE_QUOTE, "'a b   a b a'")
                ), environment));
    }

    @Test
    public void testSubstituteDoubleQuotes() throws IOException {
        Environment environment = mock(Environment.class);
        when(environment.getValue("a")).thenReturn("123");
        when(environment.getValue("b")).thenReturn("456");
        when(environment.getValue("c")).thenReturn("");
        when(environment.getValue("aba")).thenReturn("");
        Lexer lexer = new LexerImpl();
        assertEquals(Arrays.asList(
                        new Lexeme(Lexeme.Type.TEXT, "123 text b 456123"),
                        new Lexeme(Lexeme.Type.TEXT, ""),
                        new Lexeme(Lexeme.Type.TEXT, "a b   a b a")
                ),
                lexer.substitute(Arrays.asList(
                        new Lexeme(Lexeme.Type.DOUBLE_QUOTE, "\"$a text b $b$c$a\""),
                        new Lexeme(Lexeme.Type.DOUBLE_QUOTE, "\"$aba\""),
                        new Lexeme(Lexeme.Type.DOUBLE_QUOTE, "\"a b   a b a\"")
                ), environment));
    }

    @Test
    public void testSubstituteSubstitution() throws IOException {
        Environment environment = mock(Environment.class);
        when(environment.getValue("a")).thenReturn("123");
        when(environment.getValue("b")).thenReturn("456");
        when(environment.getValue("bbb")).thenReturn("");
        Lexer lexer = new LexerImpl();
        assertEquals(Arrays.asList(
                        new Lexeme(Lexeme.Type.TEXT, "aaa123456"),
                        new Lexeme(Lexeme.Type.TEXT, "456456"),
                        new Lexeme(Lexeme.Type.TEXT, "abab")
                ),
                lexer.substitute(Arrays.asList(
                        new Lexeme(Lexeme.Type.SUBSTITUTION, "aaa$a$b"),
                        new Lexeme(Lexeme.Type.SUBSTITUTION, "$b$b$bbb"),
                        new Lexeme(Lexeme.Type.SUBSTITUTION, "abab")
                ), environment));
    }
}
