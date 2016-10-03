package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.SyntaxErrorException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class AssignmentTest {

    @Test(expected = SyntaxErrorException.class)
    public void testIncorrectArgsSize() throws IOException {
        Environment environment = mock(Environment.class);
        new Assignment().execute(Arrays.asList("a"), new ByteArrayInputStream("".getBytes()), environment);
    }

    @Test
    public void testSetValueInEnvironment() throws IOException{
        Environment environment = mock(Environment.class);
        new Assignment().execute(
                Arrays.asList("variable", "value"), new ByteArrayInputStream("".getBytes()), environment);
        verify(environment).setValue("variable", "value");
    }

    @Test
    public void testReturnEmptyInputStream() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Assignment().execute(
                Arrays.asList("variable", "value"), new ByteArrayInputStream("".getBytes()), environment);
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream("".getBytes())));
    }
}
