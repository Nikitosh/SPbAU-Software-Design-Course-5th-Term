package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AssignmentTest {

    @Test(expected = SyntaxErrorException.class)
    public void testIncorrectArgsSize() throws IOException {
        Environment environment = mock(Environment.class);
        new Assignment().execute(Arrays.asList("a"), Utilities.getEmptyInputStream(), environment);
    }

    @Test
    public void testSetValueInEnvironment() throws IOException {
        Environment environment = mock(Environment.class);
        new Assignment().execute(
                Arrays.asList("variable", "value"), Utilities.getEmptyInputStream(), environment);
        verify(environment).setValue("variable", "value");
    }

    @Test
    public void testReturnEmptyInputStream() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Assignment().execute(
                Arrays.asList("variable", "value"), Utilities.getEmptyInputStream(), environment);
        assertTrue(IOUtils.contentEquals(inputStream, Utilities.getEmptyInputStream()));
    }
}
