package com.nikitosh.spbau;

import org.junit.rules.*;

import java.io.*;

public final class TestUtilities {
    private TestUtilities() {
    }

    public static File createTemporaryFileWithContent(TemporaryFolder folder, String fileName, String content)
            throws IOException {
        File tempFile = folder.newFile(fileName);
        PrintStream outputStream = new PrintStream(new FileOutputStream(tempFile));
        outputStream.print(content);
        outputStream.close();
        return tempFile;
    }
}
