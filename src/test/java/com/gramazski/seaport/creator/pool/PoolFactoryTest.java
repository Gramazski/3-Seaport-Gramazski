package com.gramazski.seaport.creator.pool;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.port.building.Warehouse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gs on 26.12.2016.
 */
public class PoolFactoryTest {
    @Test
    public void getBuildingsPool() throws Exception {
        PoolFactory<Warehouse> warehousePoolFactory = new PoolFactory<Warehouse>();
        IPool<Warehouse> warehousesPool = warehousePoolFactory.getBuildingsPool("WAREHOUSE:10,12;WAREHOUSE:11,10;");

        Assert.assertEquals(warehousesPool.acquireResource().getCapacity(), 12);
    }

}