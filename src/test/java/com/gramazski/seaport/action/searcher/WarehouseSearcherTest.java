package com.gramazski.seaport.action.searcher;

import com.gramazski.seaport.entity.pool.IPool;
import com.gramazski.seaport.entity.pool.building.PortBuildingsPool;
import com.gramazski.seaport.entity.port.building.Warehouse;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by gs on 21.12.2016.
 */
public class WarehouseSearcherTest {
    @Test
    public void findWarehouseByUploadCount() throws Exception {
        Queue<Warehouse> warehouses = new LinkedList<Warehouse>();
        warehouses.add(new Warehouse(10, 1));
        warehouses.add(new Warehouse(5, 2));
        warehouses.add(new Warehouse(3, 3));
        warehouses.add(new Warehouse(11, 4));
        IPool<Warehouse> warehousesPool = new PortBuildingsPool<Warehouse>(warehouses);
        WarehouseSearcher warehouseSearcher = new WarehouseSearcher(warehousesPool);

        Warehouse firstWarehouse = warehouseSearcher.findWarehouseByUploadCount(7);
        Warehouse secondWarehouse = warehouseSearcher.findWarehouseByUploadCount(7);

        Assert.assertEquals(firstWarehouse.getCapacity(), 10);
        Assert.assertEquals(secondWarehouse.getCapacity(), 11);
    }

}