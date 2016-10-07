package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import com.nikitosh.spbau.Utilities;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ExternalCallTest {
    private static final String FAKE_PROGRAM_NAME = "Fake";
    private static final String PYTHON_SUM_PATH = "src/test/resources/sum.py";
    private static final String PYTHON_SUM_ARGS = "2 3\n";
    private static final String PYTHON_SUM_ANSWER = "5\r\n";

    @Test
    public void testArg() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream1 = new ExternalCall().execute(
                Arrays.asList("cat", "pom.xml"), Utilities.getEmptyInputStream(), environment);
        InputStream inputStream2 = new Cat().execute(
                Arrays.asList("pom.xml"), Utilities.getEmptyInputStream(), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
    }

    @Test
    public void testInputStream() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream = new ExternalCall().execute(Arrays.asList("python", PYTHON_SUM_PATH),
                new ByteArrayInputStream(PYTHON_SUM_ARGS.getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream(PYTHON_SUM_ANSWER.getBytes())));
    }

    @Test (expected = SyntaxErrorException.class)
    public void testNoSuchCommand() throws IOException {
        Environment environment = mock(Environment.class);
        new ExternalCall().execute(Arrays.asList(FAKE_PROGRAM_NAME), Utilities.getEmptyInputStream(), environment);
    }
}
