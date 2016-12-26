package com.gramazski.seaport.creator.berth;

import com.gramazski.seaport.creator.AbstractEntitiesFactory;
import com.gramazski.seaport.entity.port.building.Berth;
import com.gramazski.seaport.exception.DataReaderException;

/**
 * Created by gs on 26.12.2016.
 */
public class BerthFactory extends AbstractEntitiesFactory<Berth> {
    public Berth getBuilding(String parameters) throws DataReaderException {
        int berthId;

        try {
            berthId = Integer.parseInt(parameters);
        }
        catch (NumberFormatException ex){
            throw new DataReaderException("Invalid parameters. Berth can't be created. Course: " + ex.getMessage(), ex);
        }

        Berth berth = new Berth(berthId);

        return berth;
    }
}
