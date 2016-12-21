package com.gramazski.seaport.actioner.uploader;

import com.gramazski.seaport.actioner.searcher.WarehouseSearcher;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;

/**
 * Created by gs on 21.12.2016.
 */
public class BerthUploader extends Thread {
    private Berth berth;
    private WarehouseSearcher warehouseSearcher;

    @Override
    public void run(){
        Warehouse warehouse = warehouseSearcher.findWarehouseByUploadCount(berth.getMooredShip().getUploadedProductCount());
        warehouse.uploadProduct(berth.getMooredShip().getUploadedProductCount());
    }

    public BerthUploader(Berth berth, WarehouseSearcher warehouseSearcher){
        this.berth = berth;
        this.warehouseSearcher = warehouseSearcher;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }
}
