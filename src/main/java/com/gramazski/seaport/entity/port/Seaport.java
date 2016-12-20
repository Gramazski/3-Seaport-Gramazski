package com.gramazski.seaport.entity.port;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PoolResourceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 20.12.2016.
 */
public class Seaport extends Thread {
    private static final Logger logger = LogManager.getLogger(Seaport.class);
    private final IPool<Berth> berthsPool;
    private final IPool<Warehouse> warehousesPool;
    //???Use for getting new ships runtime
    private IPool<Ship> waitingShipsPool;

    public Seaport(IPool<Berth> berthsPool, IPool<Warehouse> warehousesPool, IPool<Ship> waitingShipsPool){
        this.berthsPool = berthsPool;
        this.warehousesPool = warehousesPool;
        this.waitingShipsPool = waitingShipsPool;
    }

    @Override
    public void run() {

    }

    private Ship getShip(){
        try {
             Ship ship = waitingShipsPool.acquireResource(-1);
            return ship;
        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, "Can not get ship from pool. Course: " + ex.getMessage());
        }

        return null;
    }
}
