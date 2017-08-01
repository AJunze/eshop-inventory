package com.junze.eshop.inventory.request.impl;

import com.junze.eshop.inventory.model.ProductInventory;
import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.service.ProductInventoryService;

/**
 * @Author: Junze
 * @Date: Create in 10:20 2017/6/26
 * @Description:
 */
public class ProductInventoryDBUpdateRequest implements Request{

    private ProductInventory productInventory;

    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService=productInventoryService;
    }

    @Override
    public boolean isForceFresh() {
        return false;
    }

    @Override
    public void process() {
        System.out.println("======数据库更新："+productInventory.toString());
        //删除redis缓存
        productInventoryService.removeCacheProductInventory(productInventory);

        //模拟访问
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //更新数据库
        productInventoryService.updateProductInventory(productInventory);

    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }
}
