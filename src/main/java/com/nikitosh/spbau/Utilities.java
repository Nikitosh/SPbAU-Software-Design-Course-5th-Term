package com.nikitosh.spbau;

import java.io.*;

public final class Utilities {
    private Utilities() {
    }

    public static InputStream getEmptyInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }
}
