package com.junze.eshop.inventory.dao;

/**
 * @Author: Junze
 * @Date: Create in 16:14 2017/6/23
 * @Description:
 */
public interface RedisDao {

   void set(String key,String value);

   String get(String key);

   void delete(String key);
}
