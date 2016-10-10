package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;
import org.apache.commons.io.*;
import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EchoTest {
    private static final int ARGS_NUMBER = 20;

    @Test
    public void testEmptyArgs() throws IOException {
        Environment environment = mock(Environment.class);
        InputStream inputStream = new Echo().execute(
                new ArrayList<>(), new ByteArrayInputStream("text".getBytes()), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(inputStream, new ByteArrayInputStream("\n".getBytes())));
    }

    @Test
    public void testManyArgs() throws IOException {
        Environment environment = mock(Environment.class);
        List<String> args = new ArrayList<>();
        for (int i = 0; i < ARGS_NUMBER; i++) {
            args.add(String.valueOf(i));
        }
        InputStream inputStream = new Echo().execute(args, Utilities.getEmptyInputStream(), environment);
        verify(environment, never()).setValue(anyString(), anyString());
        assertTrue(IOUtils.contentEquals(
                inputStream, new ByteArrayInputStream((String.join(" ", args) + "\n").getBytes())));
    }
}
