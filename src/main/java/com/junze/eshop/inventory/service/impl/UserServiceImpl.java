package com.junze.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.junze.eshop.inventory.dao.RedisDao;
import com.junze.eshop.inventory.mapper.UserMapper;
import com.junze.eshop.inventory.model.User;
import com.junze.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Author: Junze
 * @Date: Create in 14:49 2017/6/23
 * @Description:
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisDao redisDao;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheUserInfo() {
        redisDao.set("cache_lisi","{\"name\":\"lisi\",\"age\":28}");
        String userJSONObject = redisDao.get("cache_lisi");
        JSONObject jsonObject = JSONObject.parseObject(userJSONObject);
        User user = new User();
        user.setName(jsonObject.getString("name"));
        user.setAge(jsonObject.getInteger("age"));
        return user;
    }
}
