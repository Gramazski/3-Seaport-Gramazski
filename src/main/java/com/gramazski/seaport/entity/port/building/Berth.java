package com.gramazski.seaport.entity.port.building;

import com.gramazski.seaport.entity.ship.Ship;

/**
 * Created by gs on 20.12.2016.
 */
public class Berth {
    private int berthId;
    private Ship mooredShip;

    public Berth(int berthId){
        this.berthId = berthId;
    }

    public void mooreShip(Ship ship){
        this.mooredShip = ship;
    }

    public Ship getMooredShip() {
        return mooredShip;
    }

    public int getBerthId() {
        return berthId;
    }
}
