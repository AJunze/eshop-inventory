package com.junze.eshop.inventory.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Junze
 * @Date: Create in 17:56 2017/6/23
 * @Description:
 */
public class RequestQueue {

    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();

    //标示位map
    private Map<Integer,Boolean> flagMap = new ConcurrentHashMap<Integer,Boolean>();

    private static class Singleton{
        private static RequestQueue instance;
        static {
            instance = new RequestQueue();
        }
        public static RequestQueue getInstance(){
            return instance;
        }
    }

    public static RequestQueue getInstance(){
        return Singleton.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue){
        this.queues.add(queue);
    }

    public int queueSize(){
        return queues.size();
    }
    //获取内存队列
    public ArrayBlockingQueue getQueue(int index){
        return queues.get(index);
    }

    public Map<Integer,Boolean> getFlagMap(){
        return flagMap;
    }
}
