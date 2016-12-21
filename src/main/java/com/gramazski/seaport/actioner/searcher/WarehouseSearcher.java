package com.gramazski.seaport.actioner.searcher;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Warehouse;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 21.12.2016.
 */
public class WarehouseSearcher {
    private IPool<Warehouse> warehousesPool;
    private Lock locking = new ReentrantLock();

    public WarehouseSearcher(IPool<Warehouse> warehousesPool){
        this.warehousesPool = warehousesPool;
    }

    public Warehouse findWarehouseByUploadCount(int uploadCount){
        Warehouse warehouse = null;
        locking.lock();
        try {

        }
        finally {
            locking.unlock();
        }

        return warehouse;
    }
}
