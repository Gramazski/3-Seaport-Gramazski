package com.gramazski.seaport.logic;

import com.gramazski.seaport.creator.pool.PoolFactory;
import com.gramazski.seaport.creator.warehouse.WarehouseFactory;
import com.gramazski.seaport.entity.pool.building.PortBuildingsPool;
import com.gramazski.seaport.entity.port.Seaport;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.entity.port.entering.PortEnteringPoint;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.DataReaderException;
import com.gramazski.seaport.exception.PortThreadingException;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by gs on 26.12.2016.
 */
public class SeaportProjectLogic {
    public static void main(String[] args){
        PortEnteringPoint portEnteringPoint = null;

        try {
            Semaphore portEnteringSemaphore = new Semaphore(10, true);
            portEnteringSemaphore.acquire(10);
            portEnteringPoint = new PortEnteringPoint(portEnteringSemaphore, new PortBuildingsPool<Ship>(new LinkedList<Ship>()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Seaport seaport = initSeaport(portEnteringPoint);

        if (seaport != null){
            seaport.start();
        }

        Ship ship;

        try {
            for (int i = 0; i < 10; i++){
                ship = new Ship(10, i);
                ship.setUploadedProductCount(5);
                portEnteringPoint.mooreShip(ship);
            }
        } catch (PortThreadingException e) {
            e.printStackTrace();
        }

    }

    private static Seaport initSeaport(PortEnteringPoint portEnteringPoint){
        PoolFactory<Berth> berthPoolFactory = new PoolFactory<Berth>();
        PoolFactory<Warehouse> warehousePoolFactory = new PoolFactory<Warehouse>();
        WarehouseFactory warehouseFactory = new WarehouseFactory();
        Seaport seaport = null;

        try {

            seaport = new Seaport(berthPoolFactory.getBuildingsPool("BERTH:11;BERTH:12;"),
                    warehouseFactory.getBuilding("12,130"), portEnteringPoint);
        }
        catch (DataReaderException ex) {
            //Add visible description
            ex.printStackTrace();
        }

        return seaport;
    }
}
