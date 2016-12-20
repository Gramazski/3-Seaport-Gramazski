package com.gramazski.seaport.entity.port.building;

/**
 * Created by gs on 20.12.2016.
 */
public class Warehouse {
    private int capacity;
    private int uploadedProductCount;
    private int warehouseId;

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
        if ((this.uploadedProductCount + uploadedProductCount > capacity) || (uploadedProductCount < 0)){
            return false;
        }

        this.uploadedProductCount += uploadedProductCount;

        return true;
    }

    public int getFreeSpaceCount(){
        return capacity - uploadedProductCount;
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
