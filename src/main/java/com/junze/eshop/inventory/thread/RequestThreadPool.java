package com.junze.eshop.inventory.thread;

import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Junze
 * @Date: Create in 17:03 2017/6/23
 * @Description:
 */
public class RequestThreadPool {
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public RequestThreadPool(){
        RequestQueue requestQueue = RequestQueue.getInstance();

        for(int i=0;i<10;i++){
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new WorkThread(queue));
        }
    }

    private static class Singleton{
        private static RequestThreadPool instance;
        static {
            instance = new RequestThreadPool();
        }
        public static RequestThreadPool getInstance(){
            return instance;
        }
    }
    public static RequestThreadPool getInstance(){
        return Singleton.getInstance();
    }
    public static void init(){
        getInstance();
    }
}
