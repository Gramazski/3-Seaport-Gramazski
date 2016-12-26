package com.gramazski.seaport.creator.pool;

import com.gramazski.seaport.creator.AbstractSeaportBuldingFactory;
import com.gramazski.seaport.creator.key.SeaportEntity;
import com.gramazski.seaport.creator.storage.FactoriesStorage;
import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.pool.building.PortBuildingsPool;
import com.gramazski.seaport.exception.DataReaderException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by gs on 26.12.2016.
 */
public class PoolFactory<T> {
    public IPool<T> getBuildingsPool(String parameters) throws DataReaderException {
        //Add to parser
        //Buildings split with ;
        String[] buildingItemsParameters = parameters.split(";");
        AbstractSeaportBuldingFactory<T> factory;
        Queue<T> buildingsList = new LinkedList<T>();

        try{
            for (int i = 0; i < buildingItemsParameters.length; i++){
                //Key split with :
                String[] buildingDescriptionParameters = buildingItemsParameters[i].split(":");
                factory = FactoriesStorage.getInstance().getFactory(SeaportEntity.valueOf(buildingDescriptionParameters[0]));
                buildingsList.add(factory.getBuilding(buildingDescriptionParameters[1]));
            }
        }
        catch (ArrayIndexOutOfBoundsException ex){
            throw new DataReaderException("Invalid parameters count. Parameters: " + parameters);
        }

        IPool<T> buildingsPool = new PortBuildingsPool<T>(buildingsList);

        return buildingsPool;
    }
}
