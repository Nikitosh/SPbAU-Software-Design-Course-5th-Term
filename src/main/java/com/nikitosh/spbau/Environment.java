package com.nikitosh.spbau;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, String> variableValues = new HashMap<>();

    public String getValue(String variable) {
        if (variableValues.containsKey(variable)) {
            return variableValues.get(variable);
        }
        return "";
    }

    public void setValue(String variable, String value) {
        variableValues.put(variable, value);
    }
}
