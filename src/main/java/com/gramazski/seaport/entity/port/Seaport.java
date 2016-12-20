package com.gramazski.seaport.entity.port;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 20.12.2016.
 */
public class Seaport extends Thread {
    private static final Logger logger = LogManager.getLogger(Seaport.class);
    private final IPool<Berth> berthsPool;
    private final IPool<Warehouse> warehousesPool;

    public Seaport(IPool<Berth> berthsPool, IPool<Warehouse> warehousesPool){
        this.berthsPool = berthsPool;
        this.warehousesPool = warehousesPool;
    }

    @Override
    public void run() {

    }
}
