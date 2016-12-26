package com.gramazski.seaport.creator.storage;

import com.gramazski.seaport.creator.AbstractSeaportBuldingFactory;
import com.gramazski.seaport.creator.berth.BerthFactory;
import com.gramazski.seaport.creator.key.SeaportEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 26.12.2016.
 */
public class FactoriesStorage {
    private static Lock locking = new ReentrantLock();
    private Map<SeaportEntity, AbstractSeaportBuldingFactory> factoriesMap;
    private static FactoriesStorage instance;

    private FactoriesStorage(){
        factoriesMap = new HashMap<SeaportEntity, AbstractSeaportBuldingFactory>();
        factoriesMap.put(SeaportEntity.BERTH, new BerthFactory());
        factoriesMap.put(SeaportEntity.WAREHOUSE, new BerthFactory());
    }

    public static FactoriesStorage getInstance(){
        if (instance == null){
            locking.lock();
            if (instance == null){
                instance = new FactoriesStorage();
            }
        }

        return instance;
    }

    public AbstractSeaportBuldingFactory getFactory(SeaportEntity key){
        if (factoriesMap.containsKey(key)){
            return factoriesMap.get(key);
        }

        return null;
    }

    public boolean addFactory(SeaportEntity key, AbstractSeaportBuldingFactory factory){
        //Add abstract checking
        if (!factoriesMap.containsKey(key)){
            factoriesMap.put(key, factory);
            return true;
        }

        return false;
    }
}
