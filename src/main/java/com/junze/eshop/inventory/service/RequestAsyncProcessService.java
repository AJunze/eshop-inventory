package com.junze.eshop.inventory.service;

import com.junze.eshop.inventory.request.Request;

/**
 * @Author: Junze
 * @Date: Create in 9:29 2017/6/27
 * @Description:请求异步执行
 */
public interface RequestAsyncProcessService {

    void process(Request request);
}
