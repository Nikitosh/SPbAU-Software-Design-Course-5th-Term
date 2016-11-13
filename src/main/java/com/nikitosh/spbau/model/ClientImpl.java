package com.nikitosh.spbau.model;

import org.apache.logging.log4j.*;

import java.io.*;
import java.net.*;

public class ClientImpl implements Client {
    private static final Logger LOGGER = LogManager.getLogger(ClientImpl.class);

    private Socket socket;
    private Controller controller;

    /**
     * Creates new client.
     *
     * @param controller controller through which communication with server happens.
     */
    public ClientImpl(Controller controller) {
        this.controller = controller;
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
        LOGGER.info("Connected to server with ip " + ip);
        controller.runOnConnectToServer();
        controller.run(socket);
    }

    @Override
    public void disconnect() {
        LOGGER.info("Disconnected from server");
        controller.stop();
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
