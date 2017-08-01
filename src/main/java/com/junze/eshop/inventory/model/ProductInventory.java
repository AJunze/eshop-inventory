package com.junze.eshop.inventory.model;

/**
 * @Author: Junze
 * @Date: Create in 10:34 2017/6/26
 * @Description:
 */
public class ProductInventory {

    private Integer productId;

    private Long inventoryCnt;

    public ProductInventory(){}

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "productId=" + productId +
                ", inventoryCnt=" + inventoryCnt +
                '}';
    }
}
