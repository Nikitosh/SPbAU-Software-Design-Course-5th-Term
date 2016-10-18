package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.junit.*;
import org.junit.rules.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CatTest {
    private static final int FILES_NUMBER = 20;
    private static final String FAKE_FILE_NAME = "FakeFileName";
    private static final String CONTENT = "line1\nline2 line2\n";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testEmptyArgs() throws IOException {
        String content = CONTENT;
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Cat().execute(
                new ArrayList<>(), new ByteArrayInputStream(content.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(content.getBytes())));
    }

    @Test
    public void testOneFile() throws IOException {
        String content = CONTENT;
        File file = TestUtilities.createTemporaryFileWithContent(testFolder, "file.txt", content);
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Cat().execute(
                Arrays.asList(file.getPath()), new ByteArrayInputStream("text".getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(content.getBytes())));
    }

    @Test
    public void testManyFiles() throws IOException {
        StringBuilder totalContent = new StringBuilder();
        List<File> files = new ArrayList<>();
        for (int i = 0; i < FILES_NUMBER; i++) {
            String content = String.valueOf(i);
            files.add(TestUtilities.createTemporaryFileWithContent(testFolder, "file" + content + ".txt", content));
            totalContent.append(content);
        }
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Cat().execute(files.stream().map(File::getPath).collect(Collectors.toList()),
                Utilities.getEmptyInputStream(), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(totalContent.toString().getBytes())));
    }

    @Test(expected = SyntaxErrorException.class)
    public void testNoSuchFile() throws IOException {
        Environment environment = mock(Environment.class);
        new Cat().execute(Arrays.asList(FAKE_FILE_NAME), Utilities.getEmptyInputStream(), environment);
    }
}
