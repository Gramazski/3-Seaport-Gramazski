package com.gramazski.seaport.exception;

/**
 * Created by gs on 21.12.2016.
 */
public class PortThreadingException extends Exception {
    public PortThreadingException() {
    }

    public PortThreadingException(String message, Throwable exception) {
        super(message, exception);
    }

    public PortThreadingException(String message) {
        super(message);
    }

    public PortThreadingException(Throwable exception) {
        super(exception);
    }
}
