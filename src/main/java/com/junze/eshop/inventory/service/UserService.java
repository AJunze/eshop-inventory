package com.junze.eshop.inventory.service;

import com.junze.eshop.inventory.model.User;

/**
 * @Author: Junze
 * @Date: Create in 14:48 2017/6/23
 * @Description:
 */
public interface UserService {

    User findUserInfo();
    User getCacheUserInfo();
}
