package com.gramazski.seaport.entity.pool;

import com.gramazski.seaport.exception.PoolResourceException;

/**
 * Created by gs on 20.12.2016.
 */
public interface IPool<T> {
    T acquireResource() throws PoolResourceException;

    void releaseResource(T resource);

    int getAvailableResourceCount();
}
