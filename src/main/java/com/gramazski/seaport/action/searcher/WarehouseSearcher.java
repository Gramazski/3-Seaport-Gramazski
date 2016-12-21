package com.gramazski.seaport.action.searcher;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.exception.PoolResourceException;

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
            while (true){
                warehouse = warehousesPool.acquireResource(-1);
                if (warehouse.getFreeSpaceCount() > uploadCount){
                    return warehouse;
                }
            }
        }
        catch (PoolResourceException ex){
            //throw PortThreadingException
        }
        finally {
            locking.unlock();
        }

        return warehouse;
    }
}
