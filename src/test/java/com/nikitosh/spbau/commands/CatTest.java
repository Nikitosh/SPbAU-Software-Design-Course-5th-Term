package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import com.nikitosh.spbau.Utilities;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CatTest {
    private static final int FILES_NUMBER = 20;
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
        File file = createTemporaryFileWithContent(testFolder, "file.txt", content);
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
            files.add(createTemporaryFileWithContent(testFolder, "file" + content + ".txt", content));
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
        new Cat().execute(Arrays.asList("FakeFileName"), Utilities.getEmptyInputStream(), environment);
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
