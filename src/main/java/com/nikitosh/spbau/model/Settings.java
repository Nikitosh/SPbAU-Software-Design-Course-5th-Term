package com.nikitosh.spbau.model;

public class Settings {
    private String name;
    private byte[] ip;

    Settings() {
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
