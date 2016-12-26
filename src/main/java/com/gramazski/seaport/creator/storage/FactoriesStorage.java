package com.gramazski.seaport.creator.storage;

import com.gramazski.seaport.creator.AbstractEntitiesFactory;
import com.gramazski.seaport.creator.berth.BerthFactory;
import com.gramazski.seaport.creator.key.SeaportEntity;
import com.gramazski.seaport.creator.warehouse.WarehouseFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 26.12.2016.
 */
public class FactoriesStorage {
    private static Lock locking = new ReentrantLock();
    private Map<SeaportEntity, AbstractEntitiesFactory> factoriesMap;
    private static FactoriesStorage instance;

    private FactoriesStorage(){
        factoriesMap = new HashMap<SeaportEntity, AbstractEntitiesFactory>();
        factoriesMap.put(SeaportEntity.BERTH, new BerthFactory());
        factoriesMap.put(SeaportEntity.WAREHOUSE, new WarehouseFactory());
    }

    public static FactoriesStorage getInstance(){
        if (instance == null){
            locking.lock();

            if (instance == null){
                instance = new FactoriesStorage();
            }

            locking.unlock();
        }

        return instance;
    }

    public AbstractEntitiesFactory getFactory(SeaportEntity key){
        if (factoriesMap.containsKey(key)){
            return factoriesMap.get(key);
        }

        return null;
    }

    public boolean addFactory(SeaportEntity key, AbstractEntitiesFactory factory){
        //Add abstract checking
        if (!factoriesMap.containsKey(key)){
            factoriesMap.put(key, factory);
            return true;
        }

        return false;
    }
}
