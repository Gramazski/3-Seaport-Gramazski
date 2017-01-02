package com.gramazski.seaport.entity.port;

import com.gramazski.seaport.action.uploader.BerthUploader;
import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.pool.building.PortBuildingsPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PoolResourceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by gs on 20.12.2016.
 */
public class Seaport extends Thread {
    private static final Logger logger = LogManager.getLogger(Seaport.class);
    private final IPool<Berth> berthsPool;
    private final Warehouse portWarehouse;
    //Use for getting new ships runtime
    private Semaphore portEnteringSemaphore;
    private IPool<Ship> waitingShipsPool;
    private IPool<BerthUploader> berthUploadersPool;

    public Seaport(IPool<Berth> berthsPool, Warehouse portWarehouse){
        this.berthsPool = berthsPool;
        this.portWarehouse = portWarehouse;
        this.portEnteringSemaphore = new Semaphore(10, true);
        this.waitingShipsPool = new PortBuildingsPool<Ship>(new LinkedList<Ship>());
    }

    @Override
    public void run() {
        while (true){
            Berth berth = mooreShipToBerth();
            if (berth != null){
                //Create uploaders thread pool. Memory problem???
                BerthUploader berthUploader = new BerthUploader(berth, portWarehouse, berthsPool);
                berthUploader.start();
            }
        }
    }

    public void mooreShip(Ship ship){
        waitingShipsPool.releaseResource(ship);
        signalAboutShipMooring();
    }

    private void signalAboutShipMooring(){
        portEnteringSemaphore.release();
    }

    private Ship getShip(){
        Ship ship = null;

        try {
            portEnteringSemaphore.acquire();
            ship = waitingShipsPool.acquireResource();
        }
        catch (InterruptedException ex){
            logger.log(Level.FATAL, "Can not get ship from pool. Course: " + ex.getMessage());
        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, "Can not get ship from pool. Course: " + ex.getMessage());
        }

        return ship;
    }

    private Berth mooreShipToBerth(){
        try {
            Ship ship = getShip();
            if (ship != null){
                Berth berth = berthsPool.acquireResource();
                berth.mooreShip(ship);
                logger.log(Level.INFO, "Ship " + ship.getShipId() + " moore to berth " + berth.getBerthId());

                return berth;
            }

        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, "Can not moore ship. Course: " + ex.getMessage());
        }

        return null;
    }
}
