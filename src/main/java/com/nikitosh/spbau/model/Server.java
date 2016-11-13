package com.nikitosh.spbau.model;

/**
 * Interface for user being server.
 */

public interface Server {
    /**
     * Launches server by creating server socket.
     */
    void start();

    /**
     * Stops server if it was working.
     */
    void stop();
}
