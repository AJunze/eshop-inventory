package com.junze.eshop.inventory.thread;

import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.request.RequestQueue;
import com.junze.eshop.inventory.request.impl.ProductInventoryCacheRefreshRequest;
import com.junze.eshop.inventory.request.impl.ProductInventoryDBUpdateRequest;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @Author: Junze
 * @Date: Create in 17:46 2017/6/23
 * @Description:
 */
public class WorkThread implements Callable<Boolean>{
    private ArrayBlockingQueue<Request> queue;

    public WorkThread(ArrayBlockingQueue<Request> queue){
        this.queue=queue;
    }
    @Override
    public Boolean call() throws Exception {
        try{
            while (true){
                Request request = queue.take();
                //isForceFresh为true，强制刷新缓存
                if (!request.isForceFresh()) {
                    //请求去重
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
                    if (request instanceof ProductInventoryDBUpdateRequest) {
                        //如果是更新数据库请求，将productId标示设置成ture
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        //如果缓存刷请求，标示不为空，而且为true，就说明之前存在更新数据库的请求
                        Boolean flag = flagMap.get(request.getProductId());
                        if (flag != null && flag) {
                            flagMap.put(request.getProductId(), false);
                        }
                        //如果缓存刷新请求，标示不为空，而且为false，说明队列中存在刷新请求
                        if (flag != null && !flag) {
                            return true;
                        }
                        //如果flag是null
                        if (flag == null) {
                            flagMap.put(request.getProductId(), false);
                        }
                    }
                }
                request.process();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
