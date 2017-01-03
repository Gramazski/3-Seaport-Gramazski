package com.gramazski.seaport.logic;

import com.gramazski.seaport.creator.pool.PoolFactory;
import com.gramazski.seaport.creator.warehouse.WarehouseFactory;
import com.gramazski.seaport.entity.port.Seaport;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.entity.ship.attributes.ActionType;
import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 26.12.2016.
 */
public class SeaportProjectLogic {
    public static void main(String[] args){
        Seaport seaport = initSeaport();

        if (seaport != null){
            seaport.start();
        }

        Ship ship;

        for (int i = 0; i < 10; i++){
            ship = new Ship(10, i);
            ship.setUploadedProductCount(5);
            ship.setActionType(ActionType.UNLOAD);
            seaport.mooreShip(ship);
        }

        seaport.terminate();

        ship = new Ship(12, 12);
        //Ship does not mooring seaport
        seaport.mooreShip(ship);
        //seaport.interrupt();
    }

    private static Seaport initSeaport(){
        PoolFactory<Berth> berthPoolFactory = new PoolFactory<Berth>();
        WarehouseFactory warehouseFactory = new WarehouseFactory();
        Seaport seaport = null;

        try {

            seaport = new Seaport(berthPoolFactory.getBuildingsPool("BERTH:11;BERTH:12;"),
                    warehouseFactory.getBuilding("12,130"));
        }
        catch (DataReaderException ex) {
            //Add visible description
            ex.printStackTrace();
        }

        return seaport;
    }
}
