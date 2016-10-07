package com.nikitosh.spbau;

import org.apache.commons.io.*;
import org.junit.*;
import org.junit.rules.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParserTest {
    private static final String FILE_CONTENT = "line1\n   line2   linelineline2  \n line3";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testExecuteAssignment() throws IOException {
        Environment environment = mock(Environment.class);
        Parser parser = new Parser();
        parser.execute(Arrays.asList(
                new Lexeme(Lexeme.Type.ASSIGNMENT, "a=123"),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.ASSIGNMENT, "b=a")
        ), environment);
        verify(environment).setValue("a", "123");
        verify(environment).setValue("b", "a");
    }

    @Test
    public void testPipeWithCatAndWc() throws IOException {
        Environment environment = mock(Environment.class);
        File file = TestUtilities.createTemporaryFileWithContent(testFolder, "example.txt", FILE_CONTENT);
        Parser parser = new Parser();
        InputStream inputStream = parser.execute(Arrays.asList(
                new Lexeme(Lexeme.Type.ASSIGNMENT, "b=3"),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.TEXT, "cat"),
                new Lexeme(Lexeme.Type.TEXT, file.getPath()),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.TEXT, "wc"),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.TEXT, "cat")
        ), environment);
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream("2 4 39\n".getBytes())));
    }

    @Test (expected = SyntaxErrorException.class)
    public void testIncorrectInput() throws IOException {
        Environment environment = mock(Environment.class);
        Parser parser = new Parser();
        parser.execute(Arrays.asList(
                new Lexeme(Lexeme.Type.TEXT, "echo"),
                new Lexeme(Lexeme.Type.TEXT, "text"),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.PIPE, "|"),
                new Lexeme(Lexeme.Type.TEXT, "wc")
        ), environment);
    }
}
