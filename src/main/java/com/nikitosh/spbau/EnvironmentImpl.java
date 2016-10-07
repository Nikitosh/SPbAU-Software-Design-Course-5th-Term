package com.nikitosh.spbau;

import java.util.*;

public class EnvironmentImpl implements Environment {
    private Map<String, String> variableValues = new HashMap<>();

    @Override
    public String getValue(String variable) {
        if (variableValues.containsKey(variable)) {
            return variableValues.get(variable);
        }
        return "";
    }

    @Override
    public void setValue(String variable, String value) {
        variableValues.put(variable, value);
    }
}
