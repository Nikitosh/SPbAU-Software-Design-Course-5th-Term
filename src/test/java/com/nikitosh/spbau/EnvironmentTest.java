package com.nikitosh.spbau;

import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class EnvironmentTest {

    @Test
    public void testNoKey() throws IOException {
        Environment environment = new EnvironmentImpl();
        assertEquals("", environment.getValue("variable"));
    }

    @Test
    public void testGetKey() throws IOException {
        Environment environment = new EnvironmentImpl();
        environment.setValue("variable", "value1");
        assertEquals("value1", environment.getValue("variable"));
        environment.setValue("variable", "value2");
        assertEquals("value2", environment.getValue("variable"));
    }
}
