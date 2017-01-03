package com.gramazski.seaport.logic;

import com.gramazski.seaport.creator.pool.PoolFactory;
import com.gramazski.seaport.creator.warehouse.WarehouseFactory;
import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.Seaport;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.DataReaderException;
import com.gramazski.seaport.exception.PoolResourceException;
import com.gramazski.seaport.parser.AbstractDataParser;
import com.gramazski.seaport.parser.data.TextDataParser;
import com.gramazski.seaport.reader.AbstractReader;
import com.gramazski.seaport.reader.file.TextFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 26.12.2016.
 */
public class SeaportProjectLogic {
    public static void main(String[] args){
        final Logger logger = LogManager.getLogger(SeaportProjectLogic.class);
        AbstractReader reader = new TextFileReader();
        AbstractDataParser parser = new TextDataParser();
        String[] data;

        try {
            data = parser.parse(reader.read("src/main/resources/data/seaport.txt"), " ");
            Seaport seaport = initSeaport(data[0], data[1]);
            PoolFactory<Ship> shipPoolFactory = new PoolFactory<Ship>();
            IPool<Ship> shipsPool = shipPoolFactory.getBuildingsPool(data[2]);

            if (seaport != null){
                seaport.start();
            }

            Ship ship;
            int shipsCount = shipsPool.getAvailableResourceCount();

            for (int i = 0; i < shipsCount; i++){
                ship = shipsPool.acquireResource();
                seaport.mooreShip(ship);
            }

            /*for (int i = 0; i < 10; i++){
                ship = new Ship(10, i);
                ship.setUploadedProductCount(5);
                ship.setActionType(ActionType.UNLOAD);
                seaport.mooreShip(ship);
            }

            ship = new Ship(200, 20);
            ship.setUploadedProductCount(100);
            ship.setActionType(ActionType.UNLOAD);
            seaport.mooreShip(ship);

            for (int i = 10; i < 14; i++){
                ship = new Ship(10, i);
                //ship.setUploadedProductCount(5);
                ship.setActionType(ActionType.UPLOAD);
                seaport.mooreShip(ship);
            }*/
            seaport.terminate();

            ship = new Ship(12, 12);
            //Ship does not mooring seaport
            seaport.mooreShip(ship);
        }
        catch (DataReaderException ex){
            logger.log(Level.ERROR, ex.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException ex){
            logger.log(Level.ERROR, ex.getMessage());
        }
        catch (PoolResourceException ex){
            logger.log(Level.ERROR, ex.getMessage());
        }
    }

    private static Seaport initSeaport(String berthData, String warehouseData) throws DataReaderException{
        PoolFactory<Berth> berthPoolFactory = new PoolFactory<Berth>();
        WarehouseFactory warehouseFactory = new WarehouseFactory();
        Seaport seaport = null;

        seaport = new Seaport(berthPoolFactory.getBuildingsPool(berthData),
                warehouseFactory.getBuilding(warehouseData));

        return seaport;
    }
}
