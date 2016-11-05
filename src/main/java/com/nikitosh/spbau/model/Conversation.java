package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;
import java.util.function.*;

public class Conversation {
    private static final Logger LOGGER = LogManager.getLogger(ChatServer.class);

    private boolean isRunning = false;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Consumer<Message> onReceiveMessage;

    public Conversation() {}

    public void run(Socket socket) {
        isRunning = true;
        while (isRunning) {
            try {
                receiveMessage();
            } catch (IOException exception) {
                LOGGER.warn("Failed to receive new message: " + exception.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    private Message receiveMessage() throws IOException {
        Message message = Message.read(inputStream);
        onReceiveMessage.accept(message);
        return message;
    }

    private void sendMessage(Message message) throws IOException {
        message.write(outputStream);
    }

    public void setOnReceiveMessage(Consumer<Message> onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }
}
