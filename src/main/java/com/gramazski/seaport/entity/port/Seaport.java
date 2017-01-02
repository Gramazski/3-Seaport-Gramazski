package com.gramazski.seaport.entity.port;

import com.gramazski.seaport.action.uploader.BerthUploader;
import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.entity.port.entering.PortEnteringPoint;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PoolResourceException;
import com.gramazski.seaport.exception.PortThreadingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 20.12.2016.
 */
public class Seaport extends Thread {
    private static final Logger logger = LogManager.getLogger(Seaport.class);
    private final IPool<Berth> berthsPool;
    //Create wrapper for warehouses pool as singleton
    private final Warehouse portWarehouse;
    //Use for getting new ships runtime
    private PortEnteringPoint portEnteringPoint;
    private IPool<BerthUploader> berthUploadersPool;

    public Seaport(IPool<Berth> berthsPool, Warehouse portWarehouse, PortEnteringPoint portEnteringPoint){
        this.berthsPool = berthsPool;
        this.portWarehouse = portWarehouse;
        this.portEnteringPoint = portEnteringPoint;
    }

    @Override
    public void run() {
        //Test and change
        while (true){
            Berth berth = mooreShip();
            //Create uploaders thread pool. Memory problem???
            BerthUploader berthUploader = new BerthUploader(berth, portWarehouse, berthsPool);
            berthUploader.start();
        }

    }

    private Ship getShip(){
        try {
            //Test waiting time - 0, -1
            Ship ship = portEnteringPoint.getMooredShip();
            return ship;
        }
        catch (PortThreadingException ex){
            logger.log(Level.ERROR, "Can not get ship from pool. Course: " + ex.getMessage());
        }

        return null;
    }

    private Berth mooreShip(){
        try {
            Ship ship = getShip();
            Berth berth = berthsPool.acquireResource();
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
