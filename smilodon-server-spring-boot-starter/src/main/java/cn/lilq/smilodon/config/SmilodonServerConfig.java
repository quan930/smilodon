package cn.lilq.smilodon.config;

import cn.lilq.smilodon.SubscribeService;
import cn.lilq.smilodon.pojo.Status;
import cn.lilq.smilodon.properties.SmilodonInstanceProperties;
import cn.lilq.smilodon.properties.SmilodonServerProperties;
import cn.lilq.smilodon.service.SmilodonService;
import cn.lilq.smilodon.service.impl.SmilodonServiceImpl;
import cn.lilq.smilodon.serviceregistry.SmilodonServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:06
 */

@Slf4j
@EnableConfigurationProperties({SmilodonInstanceProperties.class, SmilodonServerProperties.class})//ioc注入
public class SmilodonServerConfig {

    /**
     * 服务健康状态(总体)
     * @return Status
     */
    @Bean(name = "status")
    public Status status(){
        return new Status();
    }


    /**
     * 订阅服务列表
     * @return 订阅服务列表 url
     */
    @Bean(name = "subscribeServiceList")
    public List<SubscribeService> subscribeServiceList(){
        return new CopyOnWriteArrayList<>();
    }

    /**
     * 服务注册表
     * @return Map(key:String,value:List-Registration)
     */
    @Bean(name = "serviceRegistryMap")
    public Map<String, List<Registration>> serviceRegistryMap(){
        return new ConcurrentHashMap<>();
    }

    /**
     * 服务注册中心
     * @return ServiceRegistry-Registration
     */
    @Bean(name="serviceRegistry")
    public ServiceRegistry<Registration> smilodonServiceRegistry(){
        SmilodonServiceRegistry serviceRegistry = new SmilodonServiceRegistry();
        serviceRegistry.init();//init
        return serviceRegistry;
    }

    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean(name="smilodonService")
    public SmilodonService smilodonService(){
        return new SmilodonServiceImpl();
    }
}
