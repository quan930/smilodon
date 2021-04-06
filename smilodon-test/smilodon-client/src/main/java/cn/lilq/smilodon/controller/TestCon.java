package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/4/6 16:48
 */

@RestController
public class TestCon {
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int serverPort;
    @Value("${spring.application.name}")
    private String serverName;

    @GetMapping("/")
    public Response getServices(){
        return new Response(200,"successful",discoveryClient.getServices());
    }

    @GetMapping("/{id}")
    public Response getInstances(@PathVariable String id){
        return new Response(200,"successful",discoveryClient.getInstances(id));
    }

    @GetMapping("/hello")
    public Response hello() {
        return new Response(200,"successful","hello world,port:"+serverPort+"-serviceId:"+serverName);
    }

    @GetMapping("/sayhello")
    public Response sayHello() {
        Response response = restTemplate.getForObject("http://ORDER-SERVER/hello", Response.class);
        System.out.println(response);
        return response;
    }
}
