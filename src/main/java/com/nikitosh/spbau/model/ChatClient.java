package com.nikitosh.spbau.model;

/**
 * Interface for user being client.
 */

public interface ChatClient {
    /**
     * Connects to server with given IP and port.
     *
     * @param ip server's IP.
     * @param port server's port.
     */
    void connect(byte[] ip, int port);

    /**
     * Disconnects from server if connected.
     */
    void disconnect();
}
