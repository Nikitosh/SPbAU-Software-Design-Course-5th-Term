package com.nikitosh.spbau.model;

import java.io.*;

/**
 * Class for message.
 * Basically has two fields: name of user which sends the message and message's text itself.
 */

public class Message {
    public static final int P = 239017;

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

    /**
     * Writes message's data to given output stream.
     *
     * @param outputStream stream, message should be written to.
     * @throws IOException
     */
    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(name);
        outputStream.writeUTF(text);
    }

    /**
     * Reads message from given input stream.
     *
     * @param inputStream stream, message should be read from.
     * @return message that was read.
     * @throws IOException
     */
    public static Message read(DataInputStream inputStream) throws IOException {
        return new Message(inputStream.readUTF(), inputStream.readUTF());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return name.equals(message.name) && text.equals(message.text);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * P + text.hashCode();
    }
}
