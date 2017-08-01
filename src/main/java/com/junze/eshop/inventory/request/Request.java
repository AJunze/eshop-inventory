package com.junze.eshop.inventory.request;

/**
 * @Author: Junze
 * @Date: Create in 17:44 2017/6/23
 * @Description:
 */
public interface Request {
    boolean isForceFresh();
    void process();
    Integer getProductId();
}
