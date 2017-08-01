package com.junze.eshop.inventory.controller;

import com.junze.eshop.inventory.model.User;
import com.junze.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Junze
 * @Date: Create in 14:52 2017/6/23
 * @Description:
 */
@Controller
public class userController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public User getUser(){
        User userInfo = userService.findUserInfo();
        return userInfo;
    }

    @RequestMapping("/getCacheUserInfo")
    @ResponseBody
    public User getCacheUser(){
        User userInfo = userService.getCacheUserInfo();
        return userInfo;
    }
}
