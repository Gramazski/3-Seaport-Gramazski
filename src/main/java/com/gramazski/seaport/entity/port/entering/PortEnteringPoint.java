package com.gramazski.seaport.entity.port.entering;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PortThreadingException;

import java.util.concurrent.Semaphore;

/**
 * Created by gs on 21.12.2016.
 */
public class PortEnteringPoint {
    private Semaphore portEnteringSemaphore;
    private IPool<Ship> waitingShipsPool;

    public PortEnteringPoint(Semaphore portEnteringSemaphore){
        this.portEnteringSemaphore = portEnteringSemaphore;
    }

    public void signalAboutShipMooring() throws PortThreadingException {
        try {
            portEnteringSemaphore.acquire();
        } catch (InterruptedException ex) {
            throw new PortThreadingException("Error in signaling about ship mooring. Course: " + ex.getMessage());
        }
    }
}
