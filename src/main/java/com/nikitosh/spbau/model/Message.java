package com.nikitosh.spbau.model;

import java.io.*;

public class Message {
    private String name;
    private String text;

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(name);
        outputStream.writeUTF(text);
    }

    public static Message read(DataInputStream inputStream) throws IOException {
        return new Message(inputStream.readUTF(), inputStream.readUTF());
    }
}
