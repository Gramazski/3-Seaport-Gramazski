package com.gramazski.seaport.action.uploader;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 21.12.2016.
 */
public class BerthUploader extends Thread {
    private Berth berth;
    private Warehouse warehouse;
    private IPool<Berth> berthsPool;
    private static final Logger logger = LogManager.getLogger(BerthUploader.class);
    //Add method for choosing warehouse action

    public BerthUploader(Berth berth, Warehouse warehouse, IPool<Berth> berthsPool){
        this.berth = berth;
        this.warehouse = warehouse;
        this.berthsPool = berthsPool;
    }

    @Override
    public void run(){
        //Add map with delegates for uploading and unloading
        logger.log(Level.INFO, "Warehouse for berth - " + berth.getBerthId() + " founded. Warehouse id - "
                + warehouse.getWarehouseId() + " with capacity - " + warehouse.getFreeSpaceCount()
                + ". Ship - " + berth.getMooredShip().getShipId() + ".");
        warehouse.uploadProduct(berth.getMooredShip().getUploadedProductCount());
        logger.log(Level.INFO, "Product uploaded count: " + berth.getMooredShip().getUploadedProductCount()
                + ". On berth - " + berth.getBerthId() + ". In warehouse - " + warehouse.getWarehouseId()
                + " with capacity - " + warehouse.getFreeSpaceCount() + ". Ship - " + berth.getMooredShip().getShipId() + ".");
        berthsPool.releaseResource(berth);
        this.interrupt();
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }
}
