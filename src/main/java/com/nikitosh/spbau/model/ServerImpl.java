package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;

public class ServerImpl implements Server {
    private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

    private ServerSocket serverSocket;
    private int port;
    private Controller controller;

    public ServerImpl(Controller controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException exception) {
            LOGGER.fatal("Failed to create ServerSocket: " + exception.getMessage());
            System.exit(1);
        }
        try {
            Socket socket = serverSocket.accept();
            controller.runOnClientConnected();
            controller.run(socket);
        } catch (IOException exception) {
            LOGGER.warn("Exception during accepting client socket: " + exception.getMessage());
        }
    }

    @Override
    public void stop() {
        controller.stop();
        if (serverSocket == null) {
            return;
        }
        try {
            serverSocket.close();
        } catch (IOException exception) {
            LOGGER.warn("Exception during closing ServerSocket: " + exception.getMessage());
        }
    }
}
