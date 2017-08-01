package com.junze.eshop.inventory.request.impl;

import com.junze.eshop.inventory.model.ProductInventory;
import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.service.ProductInventoryService;

/**
 * @Author: Junze
 * @Date: Create in 14:14 2017/6/26
 * @Description:
 */
public class ProductInventoryCacheRefreshRequest implements Request{

    private Integer productId;

    private Boolean forceFresh;

    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceFresh = false;
    }

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService, Boolean forceFresh) {
        this.productId = productId;
        this.forceFresh = forceFresh;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public boolean isForceFresh() {
        return forceFresh;
    }

    @Override
    public void process() {

        //从数据库中查询商品数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        System.out.println("数据库查询到的商品："+productInventory.toString());
        //把最新的商品数量更新到缓存中
        productInventoryService.setCacheProductInventory(productInventory);


    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    public Boolean getForceFresh() {
        return forceFresh;
    }
}
