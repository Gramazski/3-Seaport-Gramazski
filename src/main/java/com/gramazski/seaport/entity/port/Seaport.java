package com.gramazski.seaport.entity.port;

import com.gramazski.seaport.action.searcher.WarehouseSearcher;
import com.gramazski.seaport.action.uploader.BerthUploader;
import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PoolResourceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

/**
 * Created by gs on 20.12.2016.
 */
public class Seaport extends Thread {
    private static final Logger logger = LogManager.getLogger(Seaport.class);
    private final IPool<Berth> berthsPool;
    //Create wrapper for warehouses pool as singleton
    private final IPool<Warehouse> warehousesPool;
    //Use for getting new ships runtime
    private IPool<Ship> waitingShipsPool;
    private IPool<BerthUploader> berthUploadersPool;
    private Semaphore enteringPoint;

    public Seaport(IPool<Berth> berthsPool, IPool<Warehouse> warehousesPool, IPool<Ship> waitingShipsPool, Semaphore enteringPoint){
        this.berthsPool = berthsPool;
        this.warehousesPool = warehousesPool;
        this.waitingShipsPool = waitingShipsPool;
        this.enteringPoint = enteringPoint;
    }

    @Override
    public void run() {
        //Test and change
        while (enteringPoint.tryAcquire()){
            Berth berth = mooreShip();
            //Create uploaders thread pool. Memory problem???
            BerthUploader berthUploader = new BerthUploader(berth, new WarehouseSearcher(warehousesPool));
            berthUploader.start();
            berthsPool.releaseResource(berthUploader.getBerth());
            if (berthUploader.isInterrupted()){
                berthUploader.interrupt();
            }
        }

    }

    private Ship getShip(){
        try {
            //Test waiting time - 0, -1
            Ship ship = waitingShipsPool.acquireResource(1000);
            return ship;
        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, "Can not get ship from pool. Course: " + ex.getMessage());
        }

        return null;
    }

    private Berth mooreShip(){
        try {
            Ship ship = getShip();
            Berth berth = berthsPool.acquireResource(-1);
            berth.mooreShip(ship);
            logger.log(Level.INFO, "Ship " + ship.getShipId() + " moore to berth " + berth.getBerthId());

            return berth;
        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, "Can not moore ship. Course: " + ex.getMessage());
        }

        return null;//Change on throwing exception
    }
}
