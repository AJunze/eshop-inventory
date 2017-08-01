package com.junze.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.junze.eshop.inventory.dao.RedisDao;
import com.junze.eshop.inventory.mapper.ProductInventoryMapper;
import com.junze.eshop.inventory.model.ProductInventory;
import com.junze.eshop.inventory.service.ProductInventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Junze
 * @Date: Create in 10:50 2017/6/26
 * @Description:数据源
 */
@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService{
    @Resource
    private ProductInventoryMapper productInventoryMapper;
    @Resource
    private RedisDao redisDao;

    //数据库方法
    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        System.out.println("已修改数据库："+productInventory.toString());
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }




    //缓存方法
    @Override
    public void setCacheProductInventory(ProductInventory productInventory){
        String key="product:inventory:"+productInventory.getProductId();
        redisDao.set(key,String.valueOf(productInventory.getInventoryCnt()));
        System.out.println("从数据库更新到缓存："+productInventory.toString()+"key:"+key);
    }

    @Override
    public ProductInventory getCacheProductInventory(Integer productId) {
        Long inventoryCnt = 0L;
        String key="product:inventory:"+productId;
        String result = redisDao.get(key);
        if (result!=null&&!("".equals(result))){
             try{
                 inventoryCnt = Long.valueOf(result);
                 return new ProductInventory(productId,inventoryCnt);
             }catch (Exception e){
                 e.printStackTrace();
             }
        }
        return null;
    }

    @Override
    public void removeCacheProductInventory(ProductInventory productInventory) {
        String key="product:inventory:"+productInventory.getProductId();

        redisDao.delete(key);
        System.out.println("已删除redis缓存：key="+key);
    }
}
