package com.junze.eshop.inventory.dao.impl;

import com.junze.eshop.inventory.dao.RedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Author: Junze
 * @Date: Create in 16:15 2017/6/23
 * @Description:
 */

@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {
    @Resource
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void delete(String key) {
        jedisCluster.del(key);
    }
}
