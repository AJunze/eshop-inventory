package com.junze.eshop.inventory.mapper;

import com.junze.eshop.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Junze
 * @Date: Create in 10:28 2017/6/26
 * @Description:
 */
public interface ProductInventoryMapper {

    //查询库存数量
    ProductInventory findProductInventory(@Param("productId") Integer productId);
    //更新库存数量
    void updateProductInventory(ProductInventory productInventory);

}
