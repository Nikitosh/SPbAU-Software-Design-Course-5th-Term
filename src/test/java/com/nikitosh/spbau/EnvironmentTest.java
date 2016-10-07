package com.nikitosh.spbau;

import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class EnvironmentTest {

    @Test
    public void testNoKey() throws IOException {
        Environment environment = new EnvironmentImpl();
        assertEquals(environment.getValue("variable"), "");
    }

    @Test
    public void testGetKey() throws IOException {
        Environment environment = new EnvironmentImpl();
        environment.setValue("variable", "value1");
        assertEquals(environment.getValue("variable"), "value1");
        environment.setValue("variable", "value2");
        assertEquals(environment.getValue("variable"), "value2");
    }
}
