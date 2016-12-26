package com.gramazski.seaport.creator;

import com.gramazski.seaport.entity.pool.IPool;

/**
 * Created by gs on 26.12.2016.
 */
public abstract class AbstractPoolFactory<T> {
    public abstract IPool<T> getPool(String parameters);
}
