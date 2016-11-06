package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;
import java.util.function.*;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private volatile boolean isRunning = false;
    private Consumer<Message> onReceiveMessage;
    private Runnable onConnectToServer;
    private Runnable onClientConnected;
    private DataOutputStream outputStream;

    public Controller() {}

    public void run(Socket socket) {
        isRunning = true;
        DataInputStream inputStream;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            LOGGER.error("Failed to get streams from socket: " + exception.getMessage());
            return;
        }
        while (isRunning) {
            try {
                receiveMessage(inputStream);
            } catch (IOException exception) {
                LOGGER.warn("Failed to receive new message: " + exception.getMessage());
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    private Message receiveMessage(DataInputStream inputStream) throws IOException {
        Message message = Message.read(inputStream);
        onReceiveMessage.accept(message);
        return message;
    }

    public boolean sendMessage(Message message) {
        if (outputStream == null) {
            return false;
        }
        try {
            message.write(outputStream);
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    public void setOnReceiveMessage(Consumer<Message> onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }

    public void setOnConnectToServer(Runnable onConnectToServer) {
        this.onConnectToServer = onConnectToServer;
    }

    public void setOnClientConnected(Runnable onClientConnected) {
        this.onClientConnected = onClientConnected;
    }

    public void runOnConnectToServer() {
        onConnectToServer.run();
    }

    public void runOnClientConnected() {
        onClientConnected.run();
    }
}
