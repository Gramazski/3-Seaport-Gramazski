package com.gramazski.seaport.entity.pool.building;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.exception.PoolResourceException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gs on 20.12.2016.
 */
public class PortBuildingsPool<T> implements IPool<T> {
    private final int POOL_SIZE; 
    private final Semaphore semaphore;
    private final Queue<T> portBuildings = new LinkedList<T>();
    private Lock locking = new ReentrantLock();
    
    public PortBuildingsPool(Queue<T> portBuildingsList) {
        POOL_SIZE = portBuildingsList.size();
        semaphore = new Semaphore(POOL_SIZE, true);
        portBuildings.addAll(portBuildingsList);
    }
    
    public T acquireResource(long maxWait) throws PoolResourceException {
        try {
            locking.lock();
            if (semaphore.tryAcquire(maxWait, TimeUnit.MILLISECONDS)) {
                T portBuilding = portBuildings.poll();

                return portBuilding;
            }
        } catch (InterruptedException ex) {
            throw new PoolResourceException(ex);
        }
        finally {
            locking.unlock();
        }

        throw new PoolResourceException("Pool wait time out. No one free resource. Waiting time: " + maxWait);
    }

    public void releaseResource(T resource) {
        portBuildings.add(resource);
        semaphore.release();
    }
}
