package com.gramazski.seaport.entity.port.building;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gs on 20.12.2016.
 */
public class Warehouse {
    private int capacity;
    private AtomicInteger uploadedProductCount;
    private int warehouseId;
    private static final Logger logger = LogManager.getLogger(Warehouse.class);

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
        controlProductCount();

        if (this.uploadedProductCount.get() + uploadedProductCount > capacity){
            if (uploadedProductCount > capacity){
                uploadedProductCount = capacity;
            }

            updateWarehouse(-uploadedProductCount);
        }

        if (uploadedProductCount < 0){
            uploadedProductCount = 0;
        }

        this.uploadedProductCount.addAndGet(uploadedProductCount);

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
            updateWarehouse(this.uploadedProductCount.get() - (this.capacity / 90));
        }

        if (this.uploadedProductCount.get() < (this.capacity / 10)){
            updateWarehouse((this.capacity / 10) - this.uploadedProductCount.get());
        }
    }

    public int unloadProduct(int unloadedProductCount){
        if (unloadedProductCount < 0){
            return 0;
        }

        if (unloadedProductCount > capacity){
            unloadedProductCount = capacity;
        }

        if (unloadedProductCount > uploadedProductCount.get()){
            updateWarehouse(uploadedProductCount.get() - unloadedProductCount);
        }

        uploadedProductCount.addAndGet(-unloadedProductCount);

        return unloadedProductCount;
    }
}
