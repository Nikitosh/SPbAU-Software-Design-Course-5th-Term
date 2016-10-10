package com.nikitosh.spbau;

/**
 * Interface, which is used for representing the environment in which Shell is running.
 * It stores such stuff as variables' values, current directory etc.
 */

public interface Environment {
    /**
     *
     * @param variable name of variable.
     *
     * @return         value of given variable.
     */
    String getValue(String variable);

    /**
     *
     * @param variable name of variable.
     * @param value    value for variable to set.
     */
    void setValue(String variable, String value);
}
