package com.nikitosh.spbau.commands;

import com.beust.jcommander.*;
import com.nikitosh.spbau.*;
import org.apache.commons.io.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Implements <i> grep </i> command.
 */

public class Grep implements Command {

    /**
     * Exception for validating that <i> -A </i> parameter is non-negative.
     */
    public static class NonNegativeInteger implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            try {
                int n = Integer.parseInt(value);
                if (n < 0) {
                    throw new ParameterException("parameter " + name + " should be positive, but " + value + " found");
                }
            } catch (NumberFormatException exception) {
                throw new ParameterException("parameter " + name + " should be number, but " + value + " found");
            }
        }
    }

    @Parameter(names = "-i", description = "case insensitivity")
    private boolean caseInsensitivity = false;

    @Parameter(names = "-w", description = "search of only whole words")
    private boolean wholeWord = false;

    @Parameter(names = "-A", description = "number of lines should be printed after matching line",
            validateWith = NonNegativeInteger.class)
    private int linesAfterMatchingNumber = 0;

    @Parameter(description = "args without special name")
    private List<String> otherArgs = new ArrayList<>();

    /**
     *
     * @param  args        list of arguments for the command.
     * @param  inputStream InputStream where Command reads from.
     * @param  environment Shell environment with all current variables.
     *
     * @return             all lines that contain a match for a pattern.
     */
    @Override
    public InputStream execute(List<String> args, InputStream inputStream, Environment environment)
            throws SyntaxErrorException, IOException {
        try {
            JCommander jCommander = new JCommander(this, args.toArray(new String[args.size()]));
        } catch (ParameterException exception) {
            throw new SyntaxErrorException("grep", exception.getMessage());
        }
        if (otherArgs.isEmpty()) {
            throw new SyntaxErrorException("grep", "regexp expected");
        }

        String regex = otherArgs.get(0);
        otherArgs.remove(0);
        if (wholeWord) {
            regex = "\\b" + regex + "\\b";
        }
        Pattern pattern;
        if (!caseInsensitivity) {
            pattern = Pattern.compile(regex);
        } else {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }

        StringBuilder result = new StringBuilder();
        if (otherArgs.isEmpty()) {
            result.append(process(IOUtils.toString(inputStream), pattern));
        } else {
            for (String arg : otherArgs) {
                try {
                    result.append(process(
                            new String(Files.readAllBytes(Paths.get(arg)), StandardCharsets.UTF_8), pattern));
                } catch (InvalidPathException | NoSuchFileException exception) {
                    throw new SyntaxErrorException("grep", arg + ": no such file");
                }
            }
        }
        return new ByteArrayInputStream(result.toString().getBytes());
    }

    private String process(String content, Pattern pattern) {
        List<String> lines = Arrays.asList(content.split("\\r?\\n"));
        int lastLineToPrint = -1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            if (pattern.matcher(lines.get(i)).find()) {
                lastLineToPrint = i + linesAfterMatchingNumber;
            }
            if (i <= lastLineToPrint) {
                result.append(lines.get(i) + "\n");
            }
        }
        return result.toString();
    }
}
