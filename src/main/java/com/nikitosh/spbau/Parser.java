package com.nikitosh.spbau;

import com.nikitosh.spbau.commands.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Parser {
    private static final Map<String, Command> COMMANDS = new HashMap<String, Command>() {
        {
            put("cat", new Cat());
            put("echo", new Echo());
            put("pwd", new Pwd());
            put("wc", new Wc());
        }
    };

    private static Collector<Lexeme, List<List<Lexeme>>, List<List<Lexeme>>> splitBySeparator(
            Predicate<Lexeme> separatorPredicate) {
        return Collector.of(
                () -> new ArrayList<List<Lexeme>>(Arrays.asList(new ArrayList<>())),
                (l, elem) -> {
                    if (separatorPredicate.test(elem)) {
                        l.add(new ArrayList<>());
                    } else {
                        l.get(l.size() - 1).add(elem);
                    }
                },
                (l1, l2) -> {
                    l1.get(l1.size() - 1).addAll(l2.remove(0));
                    l1.addAll(l2);
                    return l1;
                }
        );
    }

    public InputStream execute(List<Lexeme> lexemes, Environment environment) {
        List<List<Lexeme>> splittedLexemes = lexemes.stream().collect(splitBySeparator(
                lexeme -> lexeme.getType() == Lexeme.Type.PIPE));
        InputStream inputStream = System.in;
        try {
            for (List<Lexeme> commandList : splittedLexemes) {
                if (commandList.isEmpty()) {
                    throw new SyntaxErrorException("pipe", "unexpected pipe found");
                }
                if (commandList.get(0).getType() == Lexeme.Type.ASSIGNMENT) {
                    String content = commandList.get(0).getContent();
                    int splitter = content.indexOf('=');
                    try {
                        inputStream = new Assignment().execute(
                                Arrays.asList(content.substring(0, splitter),
                                              content.substring(splitter + 1, content.length())),
                                inputStream, environment);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    continue;
                }
                Command command;
                List<Lexeme> arguments;
                if (COMMANDS.containsKey(commandList.get(0).getContent())) {
                    command = COMMANDS.get(commandList.get(0).getContent());
                    arguments = commandList.subList(1, commandList.size());
                } else {
                    command = new ExternalCall();
                    arguments = commandList;
                }
                try {
                    inputStream = command.execute(
                            arguments.stream().map(Lexeme::getContent).collect(Collectors.toList()),
                            inputStream,
                            environment);

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SyntaxErrorException exception) {
            System.err.println(exception.getMessage());
        }
        return inputStream;
    }
}
