package com.nikitosh.spbau;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class Utilities {
    private Utilities() {
    }

    public static InputStream getEmptyInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }
}
