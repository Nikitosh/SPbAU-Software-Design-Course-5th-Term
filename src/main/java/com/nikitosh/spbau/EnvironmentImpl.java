package com.nikitosh.spbau;

import java.util.*;

/**
 * Implements Environment interface with help of Map&lt;&gt;.
 */

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
