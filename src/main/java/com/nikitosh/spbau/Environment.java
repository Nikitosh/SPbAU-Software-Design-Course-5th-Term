package com.nikitosh.spbau;

public interface Environment {
    String getValue(String variable);
    void setValue(String variable, String value);
}
