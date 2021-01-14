package cn.lilq.smilodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:58
 */

@EnableSmilodonClient
@SpringBootApplication
@Controller
public class TestServerApplication {
    @Resource
    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(TestServerApplication.class,args);
    }

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Response getServices(){
        return new Response(200,"successful",discoveryClient.getServices());
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Response getInstances(@PathVariable String id){
        return new Response(200,"successful",discoveryClient.getInstances(id));
    }
}
