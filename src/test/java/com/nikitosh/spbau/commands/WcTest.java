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

public class WcTest {
    private static final String FAKE_FILE_NAME = "FakeFileName";
    private static final String CONTENT1 = "line1\nline2 line2 lineline2\n";
    private static final String CONTENT1_WC = "2 4 28\n";
    private static final String CONTENT2 = "text\n\n\nsome more text";
    private static final String CONTENT2_WC = "3 4 21\n";
    private static final String CONTENTS_WC = "5 8 49\n";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testEmptyArgs() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Wc().execute(
                new ArrayList<>(), new ByteArrayInputStream(CONTENT1.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(CONTENT1_WC.getBytes())));
    }

    @Test
    public void testOneFile() throws IOException {
        File file = createTemporaryFileWithContent(testFolder, "file.txt", CONTENT2);
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Wc().execute(
                Arrays.asList(file.getPath()), new ByteArrayInputStream("text".getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(CONTENT2_WC.getBytes())));
    }

    @Test
    public void testManyFiles() throws IOException {
        File file1 = createTemporaryFileWithContent(testFolder, "file1.txt", CONTENT1);
        File file2 = createTemporaryFileWithContent(testFolder, "file2.txt", CONTENT2);
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Wc().execute(
                Arrays.asList(file1.getPath(), file2.getPath()), new ByteArrayInputStream("".getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(
                inputStream, new ByteArrayInputStream((CONTENT1_WC + CONTENT2_WC + CONTENTS_WC).getBytes())));
    }

    @Test(expected = SyntaxErrorException.class)
    public void testNoSuchFile() throws IOException {
        Environment environment = mock(Environment.class);
        new Wc().execute(Arrays.asList(FAKE_FILE_NAME), Utilities.getEmptyInputStream(), environment);
    }

    private File createTemporaryFileWithContent(TemporaryFolder folder, String fileName, String content)
            throws IOException {
        File tempFile = folder.newFile(fileName);
        PrintStream outputStream = new PrintStream(new FileOutputStream(tempFile));
        outputStream.print(content);
        outputStream.close();
        return tempFile;
    }
}
