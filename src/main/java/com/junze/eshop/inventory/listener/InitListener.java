package com.junze.eshop.inventory.listener;

import com.junze.eshop.inventory.thread.RequestThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author: Junze
 * @Date: Create in 16:55 2017/6/23
 * @Description:
 */
public class InitListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //初始化线程池和内存队列
        RequestThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
