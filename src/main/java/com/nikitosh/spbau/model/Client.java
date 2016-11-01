package com.nikitosh.spbau.model;

public interface Client {
    void connect(byte[] ip, int port);
    void disconnect();
}
