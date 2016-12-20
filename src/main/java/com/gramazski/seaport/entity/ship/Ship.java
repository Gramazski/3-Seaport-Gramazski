package com.gramazski.seaport.entity.ship;

import com.gramazski.seaport.entity.ship.attributes.ActionType;

/**
 * Created by gs on 20.12.2016.
 */
public class Ship {
    private int capacity;
    private ActionType actionType;
    private int uploadedProductCount;

    public Ship(int capacity){
        this.capacity = capacity;
        this.actionType = ActionType.UPLOAD;
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
}
