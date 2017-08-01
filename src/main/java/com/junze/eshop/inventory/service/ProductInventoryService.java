package com.junze.eshop.inventory.service;

import com.junze.eshop.inventory.model.ProductInventory;

/**
 * @Author: Junze
 * @Date: Create in 10:43 2017/6/26
 * @Description:
 */
public interface ProductInventoryService {

    void updateProductInventory(ProductInventory productInventory);

    ProductInventory findProductInventory(Integer productId);

    void setCacheProductInventory(ProductInventory productInventory);

    ProductInventory getCacheProductInventory(Integer productId);

    void removeCacheProductInventory(ProductInventory productInventory);
}
