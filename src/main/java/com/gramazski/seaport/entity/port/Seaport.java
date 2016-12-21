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
    private final IPool<Warehouse> warehousesPool;
    //Use for getting new ships runtime
    private IPool<Ship> waitingShipsPool;
    private Semaphore enteringPoint;

    public Seaport(IPool<Berth> berthsPool, IPool<Warehouse> warehousesPool, IPool<Ship> waitingShipsPool, Semaphore enteringPoint){
        this.berthsPool = berthsPool;
        this.warehousesPool = warehousesPool;
        this.waitingShipsPool = waitingShipsPool;
        this.enteringPoint = enteringPoint;
    }

    @Override
    public void run() {
        //try{
        //Change cycle on semaphore.acquire
            while (true){
                Berth berth = mooreShip();
                BerthUploader berthUploader = new BerthUploader(berth, new WarehouseSearcher(warehousesPool));//Create uploader pool
                berthUploader.start();
                berthsPool.releaseResource(berthUploader.getBerth());
                if (berthUploader.isInterrupted()){
                    berthUploader.interrupt();
                }
            }
        //}
        /*catch (InterruptedException ex){

        }*/

    }

    private Ship getShip(){
        try {
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
            Berth berth = berthsPool.acquireResource(1000);//??
            berth.mooreShip(ship);
            return berth;
        }
        catch (PoolResourceException ex){

        }

        return null;//Change on throwing exception
    }
}
