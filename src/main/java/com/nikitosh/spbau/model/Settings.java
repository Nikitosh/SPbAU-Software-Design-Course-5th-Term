package com.nikitosh.spbau.model;

public final class Settings {
    private static Settings instance;
    private String name;
    private byte[] ip;

    private Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public byte[] getIp() {
        return ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(byte[] ip) {
        this.ip = ip;
    }
}
