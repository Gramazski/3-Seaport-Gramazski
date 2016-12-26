package com.gramazski.seaport.entity.port.building;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gs on 20.12.2016.
 */
public class Warehouse {
    private int capacity;
    //Change on atomic integer
    private int uploadedProductCount;
    private int warehouseId;
    private static final Logger logger = LogManager.getLogger(Warehouse.class);

    public Warehouse(int capacity, int warehouseId){
        this.capacity = capacity;
        this.warehouseId = warehouseId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public boolean uploadProduct(int uploadedProductCount){
        if (this.uploadedProductCount > (this.capacity / 90)){
            updateWarehouse(this.uploadedProductCount - (this.capacity / 90));
        }

        if (this.uploadedProductCount < (this.capacity / 10)){
            updateWarehouse(this.uploadedProductCount - (this.capacity / 10));
        }

        if ((this.uploadedProductCount + uploadedProductCount > capacity) || (uploadedProductCount < 0)){
            return false;
        }

        this.uploadedProductCount += uploadedProductCount;

        return true;
    }

    public int getFreeSpaceCount(){
        return capacity - uploadedProductCount;
    }

    private void updateWarehouse(int extraCount){
        uploadedProductCount += extraCount;
        logger.log(Level.INFO, "Warehouse id: " + warehouseId + ". Warehouse upload count updated by warehouse manager. Extra count: " + extraCount +
                ". Current count: " + uploadedProductCount);
    }

    public int unloadProduct(int unloadedProductCount){
        if (unloadedProductCount < 0){
            return 0;
        }

        if (unloadedProductCount > uploadedProductCount){
            return uploadedProductCount;
        }

        uploadedProductCount -= unloadedProductCount;

        return unloadedProductCount;
    }
}
