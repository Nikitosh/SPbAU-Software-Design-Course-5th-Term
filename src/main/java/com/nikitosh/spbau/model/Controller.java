package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;
import java.util.function.*;

/**
 * Class which provides ability to communicate with another user (send and receive messages)
 * and which is used for interaction between Model and UI.
 */

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private volatile boolean isRunning = false;
    /**
     * Callback for receiving message.
     */
    private Consumer<Message> onReceiveMessage;
    /**
     * Callback for connecting to server.
     */
    private Runnable onConnectToServer;
    /**
     * Callback for client connected.
     */
    private Runnable onClientConnected;
    private DataOutputStream outputStream;

    /**
     * Runs conversation between two users by trying to read new messages from another user.
     *
     * @param socket socket used for communication with another user.
     */
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

    /**
     * Stops conversation if it was in progress.
     */
    public void stop() {
        isRunning = false;
    }

    private Message receiveMessage(DataInputStream inputStream) throws IOException {
        Message message = Message.read(inputStream);
        LOGGER.info("Message was received");
        onReceiveMessage.accept(message);
        return message;
    }

    /**
     * Tries to send message to another user.
     *
     * @param message message should be sent.
     * @return if message was sent or not.
     */
    public boolean sendMessage(Message message) {
        if (outputStream == null) {
            return false;
        }
        try {
            message.write(outputStream);
            LOGGER.info("Message was sent");
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
