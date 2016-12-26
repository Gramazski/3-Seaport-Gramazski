package com.gramazski.seaport.action.searcher;

import com.gramazski.seaport.entity.port.building.Warehouse;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 21.12.2016.
 */
//Change on warehouse manager as thread
public class WarehouseSearcher {
    private Warehouse warehouse;
    private Lock locking = new ReentrantLock();

    public WarehouseSearcher(Warehouse warehouse){
        this.warehouse = warehouse;
    }

    public Warehouse findWarehouseByUploadCount(int uploadCount){

        return warehouse;
    }
}
