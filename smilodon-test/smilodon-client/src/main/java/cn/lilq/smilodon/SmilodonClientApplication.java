package cn.lilq.smilodon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/29 21:06
 */
@EnableSmilodonClient
@SpringBootApplication
@Configuration
@Controller
@EnableDiscoveryClient
public class SmilodonClientApplication {
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;
    @Value("${server.port}")
    private int serverPort;

    public static void main(String[] args) {
        SpringApplication.run(SmilodonClientApplication.class,args);
    }

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Response getServices(){
        return new Response(200,"successful",discoveryClient.getServices());
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Response getInstances(@PathVariable String id){
//        LoadBalanced
//        LoadBalancerClient client = new BlockingLoadBalancerClient(new LoadBalancerClientFactory());  //LoadBalancerClient默认实现
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("ORDER-SERVER");
//        LoadBalancerClient client = new LoadBalancerClient(serviceInstances);
//        ServiceInstance serviceInstance = client.choose("ORDER-SERVER");
//        String result = new RestTemplate().getForObject(
//                "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/hello",
//                String.class
//        );


        return new Response(200,"successful",discoveryClient.getInstances(id));
    }

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Response hello() {
        return new Response(200,"successful","hello world,port:"+serverPort);
    }

    @ResponseBody
    @RequestMapping(value = "/sayhello", method = RequestMethod.GET)
    public Response sayHello() {
        Response response = restTemplate.getForObject("http://ORDER-SERVER/hello", Response.class);
        System.out.println(response);
        return response;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
