package com.nikitosh.spbau.model;

public final class Settings {
    private static Settings instance;
    private String name = "Name";
    private byte[] serverIp = new byte[] {0, 0, 0, 0};
    private int serverPort = 0;
    private int portToConnect = 0;

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public byte[] getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getPortToConnect() {
        return portToConnect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServerIp(byte[] serverIp) {
        this.serverIp = serverIp;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setPortToConnect(int portToConnect) {
        this.portToConnect = portToConnect;
    }
}
