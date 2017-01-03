package com.gramazski.seaport.creator.ship;

import com.gramazski.seaport.creator.AbstractEntitiesFactory;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.entity.ship.attributes.ActionType;
import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 26.12.2016.
 */
public class ShipFactory extends AbstractEntitiesFactory<Ship> {
    public Ship getBuilding(String parameters) throws DataReaderException {
        String[] shipParameters = parameters.split(",");
        int shipId;
        int shipCapacity;
        int shipUploadingCount;
        ActionType shipActionType;

        try{
            shipId = Integer.parseInt(shipParameters[0]);
            shipCapacity = Integer.parseInt(shipParameters[1]);
            shipUploadingCount = Integer.parseInt(shipParameters[2]);
            shipActionType = ActionType.valueOf(shipParameters[3]);
        }
        catch (NumberFormatException ex){
            throw new DataReaderException("Invalid parameters. Warehouse can't be created. Course: " + ex.getMessage(), ex);
        }
        catch (ArrayIndexOutOfBoundsException ex){
            throw new DataReaderException("Invalid count of parameters. Berth can't be created. Course: " + ex.getMessage(), ex);
        }

        Ship ship = new Ship(shipCapacity, shipId);
        ship.setUploadedProductCount(shipUploadingCount);
        ship.setActionType(shipActionType);
        
        return ship;
    }
}
