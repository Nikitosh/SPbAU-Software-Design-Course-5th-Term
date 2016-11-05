package com.nikitosh.spbau.model;

public final class Settings {
    private static Settings instance;
    private String name = "Name";
    private byte[] serverIp = new byte[] {0, 0, 0, 0};
    private int port = 0;

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

    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServerIp(byte[] serverIp) {
        this.serverIp = serverIp;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
