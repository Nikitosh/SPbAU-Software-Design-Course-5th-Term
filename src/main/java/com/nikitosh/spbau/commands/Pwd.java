package com.nikitosh.spbau.commands;

import com.nikitosh.spbau.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Pwd implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        return new ByteArrayInputStream((Paths.get(".").toAbsolutePath().normalize().toString() + "\n").getBytes());
    }
}
