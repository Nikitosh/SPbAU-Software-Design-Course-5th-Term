package com.nikitosh.spbau;

import java.io.*;

/**
 * Helper class with some often used methods which can't be logically included in other classes.
 */

public final class Utilities {
    private Utilities() {
    }

    /**
     *
     * @return empty InputStream.
     */
    public static InputStream getEmptyInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }
}
