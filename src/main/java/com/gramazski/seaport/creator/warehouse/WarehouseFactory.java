package com.gramazski.seaport.creator.warehouse;

import com.gramazski.seaport.creator.AbstractSeaportBuldingFactory;
import com.gramazski.seaport.entity.port.building.Warehouse;
import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 26.12.2016.
 */
public class WarehouseFactory extends AbstractSeaportBuldingFactory<Warehouse> {
    public Warehouse getBuilding(String parametrs) throws DataReaderException {
        //Remove to parser
        String[] warehouseParameters = parametrs.split(",");
        int warehouseId;
        int warehouseCapacity;

        try{
            warehouseId = Integer.parseInt(warehouseParameters[0]);
            warehouseCapacity = Integer.parseInt(warehouseParameters[1]);
        }
        catch (NumberFormatException ex){
            throw new DataReaderException("Invalid parameters. Warehouse can't be created. Course: " + ex.getMessage(), ex);
        }
        catch (ArrayIndexOutOfBoundsException ex){
            throw new DataReaderException("Invalid count of parameters. Berth can't be created. Course: " + ex.getMessage(), ex);
        }

        Warehouse warehouse = new Warehouse(warehouseCapacity, warehouseId);

        return warehouse;
    }
}
