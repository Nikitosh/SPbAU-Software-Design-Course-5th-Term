package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;

public class ServerImpl implements Server {
    private static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;
    private Controller controller;

    /**
     * Creates new server.
     *
     * @param controller controller through which communication with client happens.
     * @param port port for creating server socket.
     */
    public ServerImpl(Controller controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info("Server socket was created");
        } catch (IOException exception) {
            LOGGER.error("Failed to create ServerSocket: " + exception.getMessage());
            return;
        }
        try {
            socket = serverSocket.accept();
            LOGGER.info("Client connected");
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
            if (socket != null) {
                socket.close();
            }
        } catch (IOException exception) {
            LOGGER.warn("Exception during closing socket by server: " + exception.getMessage());
        }
    }
}
