package com.gramazski.seaport.exception;

/**
 * Created by gs on 20.12.2016.
 */
public class PoolResourceException extends Exception {
    public PoolResourceException() {
    }

    public PoolResourceException(String message, Throwable exception) {
        super(message, exception);
    }

    public PoolResourceException(String message) {
        super(message);
    }

    public PoolResourceException(Throwable exception) {
        super(exception);
    }
}
