package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/10 17:27
 */
@Controller
public class TestServerCon {
    @Resource
    private DiscoveryClient discoveryClient;

    @ResponseBody
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Response test(){
        return new Response(200,"successful",discoveryClient.getServices());
    }

    @ResponseBody
    @RequestMapping(value = "/test/{id}",method = RequestMethod.GET)
    public Response test1(@PathVariable String id){
        return new Response(200,"successful",discoveryClient.getInstances(id));
    }

}
