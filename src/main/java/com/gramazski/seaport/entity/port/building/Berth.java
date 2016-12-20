package com.gramazski.seaport.entity.port.building;

import com.gramazski.seaport.entity.ship.Ship;

/**
 * Created by gs on 20.12.2016.
 */
public class Berth extends Thread {
    private int berthId;
    //????
    private int usingWarehouseId;
    //????
    private Ship mooredShip;

    public Berth(int berthId){
        this.berthId = berthId;
    }

    public void mooreShip(Ship ship){
        this.mooredShip = mooredShip;
    }
}
