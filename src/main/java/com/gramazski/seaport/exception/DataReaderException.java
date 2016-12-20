package com.gramazski.seaport.exception;

/**
 * Created by gs on 20.12.2016.
 */
public class DataReaderException extends Exception {
    public DataReaderException() {
    }

    public DataReaderException(String message, Throwable exception) {
        super(message, exception);
    }

    public DataReaderException(String message) {
        super(message);
    }

    public DataReaderException(Throwable exception) {
        super(exception);
    }
}
