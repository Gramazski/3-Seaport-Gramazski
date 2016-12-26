package com.gramazski.seaport.action.uploader;

import com.gramazski.seaport.action.searcher.WarehouseSearcher;
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
    private WarehouseSearcher warehouseSearcher;
    private static final Logger logger = LogManager.getLogger(BerthUploader.class);
    //Add method for choosing warehouse action

    public BerthUploader(Berth berth, WarehouseSearcher warehouseSearcher){
        this.berth = berth;
        this.warehouseSearcher = warehouseSearcher;
    }

    @Override
    public void run(){
        //Add map with delegates for uploading and unloading
        Warehouse warehouse = warehouseSearcher.findWarehouseByUploadCount(berth.getMooredShip().getUploadedProductCount());
        logger.log(Level.INFO, "Warehouse for berth - " + berth.getBerthId() + " founded. Warehouse id - "
                + warehouse.getWarehouseId() + ". Ship - " + berth.getMooredShip().getShipId() + ".");
        warehouse.uploadProduct(berth.getMooredShip().getUploadedProductCount());
        logger.log(Level.INFO, "Product uploaded berth - " + berth.getBerthId() + ". In warehouse - "
                + warehouse.getWarehouseId() + ". Ship - " + berth.getMooredShip().getShipId() + ".");
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }
}
