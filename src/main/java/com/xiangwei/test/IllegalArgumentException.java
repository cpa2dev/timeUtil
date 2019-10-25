package com.xiangwei.test;

/**
 * A exception class used to represent a illegal argument
 */
public class IllegalArgumentException extends Exception {

    /**
     * Default constructor
     *
     * @param message Error message
     */
    public IllegalArgumentException(String message) {
        super(message);
    }
}
