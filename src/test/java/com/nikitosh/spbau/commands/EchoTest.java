package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.Environment;
import com.nikitosh.spbau.Utilities;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
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
