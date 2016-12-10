package com.nikitosh.spbau.model;

/**
 * Class for message.
 * Basically has two fields: name of user which sends the message and message's text itself.
 */

public class ChatMessage {
    public static final int P = 239017;

    private String name;
    private String text;

    public ChatMessage(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChatMessage)) {
            return false;
        }
        ChatMessage message = (ChatMessage) obj;
        return name.equals(message.name) && text.equals(message.text);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * P + text.hashCode();
    }
}
