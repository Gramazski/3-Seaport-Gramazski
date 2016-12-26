package com.gramazski.seaport.creator;

import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 26.12.2016.
 */
public abstract class AbstractSeaportBuldingFactory<T> {
    public abstract T getBuilding(String parametrs) throws DataReaderException;
}
