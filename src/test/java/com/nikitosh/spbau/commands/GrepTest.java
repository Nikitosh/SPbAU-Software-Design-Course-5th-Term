package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.junit.*;
import org.junit.rules.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GrepTest {
    private static final int FILES_NUMBER = 20;
    private static final String FAKE_FILE_NAME = "FakeFileName";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testEmptyArgs() throws IOException {
        String content = "line1\n"
                + "line2 line22\n"
                + " ne3\n";
        String regex = "e[0-9]$";
        String answer = "line1\n "
                + "ne3\n";
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Grep().execute(
                Arrays.asList(regex), new ByteArrayInputStream(content.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.getBytes())));
    }

    @Test
    public void testOneFile() throws IOException {
        String content = "line1\n"
                + "line2 line22\n"
                + " ne3\n";
        String regex = "e[0-9]$";
        String answer = "line1\n"
                + " ne3\n";
        Environment environment = mock(Environment.class);
        File file = TestUtilities.createTemporaryFileWithContent(testFolder, "file.txt", content);
        InputStream inputStream = new Grep().execute(
                Arrays.asList(regex, file.getPath()), Utilities.getEmptyInputStream(), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.getBytes())));
    }


    @Test
    public void testManyFiles() throws IOException {
        StringBuilder answer = new StringBuilder();
        List<String> args = new ArrayList<>();
        String regex = ".ne[0-9]\n";
        args.add(regex);
        for (int i = 0; i < FILES_NUMBER; i++) {
            String content = String.valueOf(i);
            args.add(TestUtilities.createTemporaryFileWithContent(
                    testFolder, "file" + content + ".txt", "line" + content + "\nne7").getPath());
            answer.append("line" + content + "\n");
        }
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Grep().execute(args, Utilities.getEmptyInputStream(), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.toString().getBytes())));
    }

    @Test(expected = SyntaxErrorException.class)
    public void testNoSuchFile() throws IOException {
        Environment environment = mock(Environment.class);
        new Grep().execute(Arrays.asList("some regex", FAKE_FILE_NAME), Utilities.getEmptyInputStream(), environment);
    }

    @Test
    public void testCaseInsensitivity() throws IOException {
        String content = "TEXT\n"
                + "xe\n"
                + "tex \n";
        String regex = ".ex.";
        String answer = "TEXT\n"
                + "tex \n";
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Grep().execute(
                Arrays.asList("-i", regex), new ByteArrayInputStream(content.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.getBytes())));
    }

    @Test
    public void testWholeWords() throws IOException {
        String content = "abacaba\n"
                + "hey aba hey\n"
                + "aba2 hey\n"
                + "hey aba\n";
        String regex = "aba";
        String answer = "hey aba hey\n"
                + "hey aba\n";
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Grep().execute(
                Arrays.asList("-w", regex), new ByteArrayInputStream(content.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.getBytes())));
    }

    @Test
    public void testLinesAfterMatching() throws IOException {
        String content = "abacaba\n"
                + "hey abac hey\n"
                + "aba2 hey\n"
                + "hey aba\n"
                + "c\n";
        String regex = "c";
        String answer = "abacaba\n"
                + "hey abac hey\n"
                + "aba2 hey\n"
                + "c\n";
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Grep().execute(
                Arrays.asList("-A", "1", regex), new ByteArrayInputStream(content.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(answer.getBytes())));
    }

    @Test (expected = SyntaxErrorException.class)
    public void testLinesAfterMatchingNotInteger() throws IOException {
        Environment environment = mock(Environment.class);
        new Grep().execute(Arrays.asList("-A", "b", "some regex"), Utilities.getEmptyInputStream(), environment);
    }

    @Test (expected = SyntaxErrorException.class)
    public void testLinesAfterMatchingNegativeInteger() throws IOException {
        Environment environment = mock(Environment.class);
        new Grep().execute(Arrays.asList("-A", "-10", "some regex"), Utilities.getEmptyInputStream(), environment);
    }
}
