package com.gramazski.seaport.reader;

import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 20.12.2016.
 */
public abstract class AbstractReader {
    public abstract String read(String sourceName) throws DataReaderException;
}
