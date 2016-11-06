package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;

public class ChatClient implements Client {
    private static final Logger LOGGER = LogManager.getLogger(ChatClient.class);

    private Socket socket;
    private Conversation conversation;

    public ChatClient(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public void connect(byte[] ip, int port) {
        try {
            socket = new Socket(InetAddress.getByAddress(ip), port);
        } catch (UnknownHostException exception) {
            LOGGER.error("Could not determine server with given ip " + ip + ": " + exception.getMessage());
            return;
        } catch (IOException exception) {
            LOGGER.error("Could not connect to server with given ip " + ip + ": " + exception.getMessage());
            return;
        }
        conversation.runOnConnectToServer();
        conversation.run(socket);
    }

    @Override
    public void disconnect() {
        conversation.stop();
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (IOException exception) {
            LOGGER.warn("Exception during closing socket: " + exception.getMessage());
        }
    }
}
