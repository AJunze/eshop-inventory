package com.junze.eshop.inventory.controller;

import com.junze.eshop.inventory.model.ProductInventory;
import com.junze.eshop.inventory.request.Request;
import com.junze.eshop.inventory.request.impl.ProductInventoryCacheRefreshRequest;
import com.junze.eshop.inventory.request.impl.ProductInventoryDBUpdateRequest;
import com.junze.eshop.inventory.service.ProductInventoryService;
import com.junze.eshop.inventory.service.RequestAsyncProcessService;
import com.junze.eshop.inventory.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: Junze
 * @Date: Create in 10:32 2017/6/27
 * @Description:
 */
@Controller
public class ProductInventoryController {

    @Resource
    private ProductInventoryService productInventoryService;

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("/updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory){

        System.out.println("========log========:更新商品库存：id"+productInventory.getProductId()+"库存数量:"+productInventory.getInventoryCnt());

        Response response = null;

        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    @RequestMapping("/getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId){
        ProductInventory productInventory = null;

        productInventory = productInventoryService.getCacheProductInventory(productId);

        //从缓存中取到数据直接返回
        if (productInventory != null){
            System.out.println("直接从缓存中读取"+productInventory.toString());
            return productInventory;
        }
        System.out.println("内存为空,进入缓存队列，尝试串行拉取数据");

        //内存为空,进入缓存队列，尝试串行拉取数据
        try {

            Request request = new ProductInventoryCacheRefreshRequest(productId,productInventoryService,false);
            requestAsyncProcessService.process(request);
            //等待从数据库将数据拉入缓存，队列串行操作

            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            //尝试从内存获取数据
            while(true){
                //等待超时
                if (waitTime>200){
                    break;
                }
                productInventory = productInventoryService.getCacheProductInventory(productId);
                if (productInventory!=null){
                    //查询结果(缓存)

                    System.out.println("200ms内从redis中获取到的数据："+productInventory.toString());

                    return productInventory;
                }
                Thread.sleep(20);
                endTime = System.currentTimeMillis();
                waitTime = endTime - startTime;
            }
            //直接从数据库查询

            System.out.println("200ms超时直接从数据库查询");
            productInventory = productInventoryService.findProductInventory(productId);
            System.out.println("200ms超时直接从数据库查询："+productInventory.toString());
            if (productInventory!=null){
                //刷新缓存
                //解决redis自动清空缓存bug
                //强制刷新缓存
                request = new ProductInventoryCacheRefreshRequest(productId,productInventoryService,true);
                requestAsyncProcessService.process(request);
                /*1、redis清空，LRU算法
                  2、redis请求积压，查询超时
                  3、数据库没有数据，缓存穿透，每次请求到达数据库*/

                while(true){
                    productInventory = productInventoryService.getCacheProductInventory(productId);
                    if (productInventory!=null){
                        //查询结果(缓存)
                        System.out.println("强制刷新后缓存从redis中获取到的数据："+productInventory.toString());
                        return productInventory;
                    }
                    Thread.sleep(20);
                }
                //查询结果(数据库)
                //return productInventory;
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ProductInventory(productId,-1l);
    }
}
