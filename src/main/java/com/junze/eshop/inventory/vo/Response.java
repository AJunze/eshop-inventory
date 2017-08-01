package com.junze.eshop.inventory.vo;

import com.sun.net.httpserver.Authenticator;

/**
 * @Author: Junze
 * @Date: Create in 10:33 2017/6/27
 * @Description:
 */
public class Response {

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
