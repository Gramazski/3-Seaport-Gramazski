package com.gramazski.seaport.entity.ship;

import com.gramazski.seaport.entity.ship.attributes.ActionType;

/**
 * Created by gs on 20.12.2016.
 */
public class Ship {
    private int capacity;
    private ActionType actionType;
    private int uploadedProductCount;
    private int shipId;

    public Ship(int capacity, int shipId){
        this.capacity = capacity;
        this.actionType = ActionType.UPLOAD;
        this.shipId = shipId;
    }

    //For test
    public void setUploadedProductCount(int uploadedProductCount) {
        this.uploadedProductCount = uploadedProductCount;
    }

    public int getShipId() {
        return shipId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUploadedProductCount() {
        return uploadedProductCount;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public boolean uploadProduct(int uploadedProductCount){
        if ((this.uploadedProductCount + uploadedProductCount > capacity) || (uploadedProductCount < 0)){
            return false;
        }

        this.uploadedProductCount += uploadedProductCount;

        return true;
    }

    public void unloadProduct(){
        this.uploadedProductCount = 0;
    }
}
