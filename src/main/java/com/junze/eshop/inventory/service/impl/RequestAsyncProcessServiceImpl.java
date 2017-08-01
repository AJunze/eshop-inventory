package com.junze.eshop.inventory.service.impl;

import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.request.RequestQueue;
import com.junze.eshop.inventory.request.impl.ProductInventoryCacheRefreshRequest;
import com.junze.eshop.inventory.request.impl.ProductInventoryDBUpdateRequest;
import com.junze.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Junze
 * @Date: Create in 9:35 2017/6/27
 * @Description:请求异步处理实现类
 */
@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService{

    @Override
    public void process(Request request) {
        //请求路由
        ArrayBlockingQueue queue = getRoutingQueue(request.getProductId());
        //将请求放入对应的队列，完成路由
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Author: Junze
     * @Date: Create in 10:04 2017/6/27
     * @param productId
     * @Description: 获取路由到的内存队列
     */
    private ArrayBlockingQueue getRoutingQueue(Integer productId){
        RequestQueue requestQueue = RequestQueue.getInstance();
        //获取hash值
        String key = String.valueOf(productId);
        int h;
        int hash =(key == null) ? 0 : (h = key.hashCode()) ^ (h>>>16);
        /*对hash进行取模
          用内存队列数量对hash值取模之后，结果一定在索引中
          所以任何商品id都会被固定路由到同一个内存队列中去的*/
        int index = (requestQueue.queueSize ()- 1) & hash;

        System.out.println("======Request加入内存队列======id:"+productId+"路由队列索引："+index);
        return requestQueue.getQueue(index);
    }
}
