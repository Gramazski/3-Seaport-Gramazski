package com.gramazski.seaport.entity.port.building;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 20.12.2016.
 */
public class Warehouse {
    private int capacity;
    private AtomicInteger uploadedProductCount;
    private int warehouseId;
    private static final Logger logger = LogManager.getLogger(Warehouse.class);
    private Lock locking = new ReentrantLock();

    public Warehouse(int capacity, int warehouseId){
        this.capacity = capacity;
        this.warehouseId = warehouseId;
        this.uploadedProductCount = new AtomicInteger(0);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public int uploadProduct(int uploadedProductCount){
        locking.lock();
        controlProductCount();

        if (this.uploadedProductCount.get() + uploadedProductCount > capacity){
            if (uploadedProductCount > capacity){
                uploadedProductCount = capacity;
            }

            updateWarehouse(capacity - (this.uploadedProductCount.get() + uploadedProductCount));
        }

        if (uploadedProductCount < 0){
            uploadedProductCount = 0;
        }

        this.uploadedProductCount.addAndGet(uploadedProductCount);

        locking.unlock();

        return uploadedProductCount;
    }

    public int getFreeSpaceCount(){
        return capacity - uploadedProductCount.get();
    }

    private void updateWarehouse(int extraCount){
        uploadedProductCount.addAndGet(extraCount);
        logger.log(Level.INFO, "Warehouse id: " + warehouseId + ". Warehouse upload count updated by warehouse manager. Extra count: " + extraCount +
                ". Current count: " + uploadedProductCount);
    }

    private void controlProductCount(){
        if (this.uploadedProductCount.get() > (this.capacity * 9 / 10)){
            updateWarehouse((this.capacity * 9 / 10) - this.uploadedProductCount.get());
        }

        if (this.uploadedProductCount.get() < (this.capacity / 10)){
            updateWarehouse((this.capacity / 10) - this.uploadedProductCount.get());
        }
    }

    public int unloadProduct(int unloadedProductCount){
        locking.lock();

        controlProductCount();
        if (unloadedProductCount < 0){
            return 0;
        }

        if (unloadedProductCount > capacity){
            unloadedProductCount = capacity;
        }

        if (unloadedProductCount > uploadedProductCount.get()){
            updateWarehouse(unloadedProductCount - uploadedProductCount.get());
        }

        uploadedProductCount.addAndGet(-unloadedProductCount);

        locking.unlock();

        return unloadedProductCount;
    }
}
