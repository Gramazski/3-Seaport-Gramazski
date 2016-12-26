package com.gramazski.seaport.entity.port.entering;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.ship.Ship;
import com.gramazski.seaport.exception.PoolResourceException;
import com.gramazski.seaport.exception.PortThreadingException;

import java.util.concurrent.Semaphore;

/**
 * Created by gs on 21.12.2016.
 */
public class PortEnteringPoint {
    private Semaphore portEnteringSemaphore;
    private IPool<Ship> waitingShipsPool;

    public PortEnteringPoint(Semaphore portEnteringSemaphore, IPool<Ship> waitingShipsPool){
        this.portEnteringSemaphore = portEnteringSemaphore;
        this.waitingShipsPool = waitingShipsPool;
    }

    public void mooreShip(Ship ship) throws PortThreadingException {
        waitingShipsPool.releaseResource(ship);
        signalAboutShipMooring();
    }

    private void signalAboutShipMooring() throws PortThreadingException {
        portEnteringSemaphore.release();
    }

    public Ship getMooredShip() throws PortThreadingException {
        Ship ship;

        try {
            portEnteringSemaphore.acquire();
            ship = waitingShipsPool.acquireResource(1000);
        } catch (InterruptedException ex) {
            throw new PortThreadingException("Error in getting moored ship. Course: " + ex.getMessage());
        }
        catch (PoolResourceException ex){
            throw new PortThreadingException("Error in getting moored ship. Course: " + ex.getMessage());
        }

        return ship;
    }
}
