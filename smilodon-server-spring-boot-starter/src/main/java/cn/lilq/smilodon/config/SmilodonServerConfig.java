package cn.lilq.smilodon.config;

import cn.lilq.smilodon.pojo.Status;
import cn.lilq.smilodon.properties.SmilodonInstanceProperties;
import cn.lilq.smilodon.properties.SmilodonServerProperties;
import cn.lilq.smilodon.serviceregistry.SmilodonServiceRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:06
 */

@EnableConfigurationProperties({SmilodonInstanceProperties.class, SmilodonServerProperties.class})//ioc注入
public class SmilodonServerConfig {
    @Resource
    private SmilodonInstanceProperties smilodonInstanceProperties;
    @Resource
    private SmilodonServerProperties smilodonServerProperties;

    /**
     * 服务健康状态(总体)
     * @return Status
     */
    @Bean(name = "status")
    public Status status(){
        return new Status();
    }

    /**
     * 服务注册表
     * @return Map<String, List<Registration>>
     */
    @Bean(name = "serviceRegistryMap")
    public ConcurrentHashMap<String, List<Registration>> serviceRegistry(){
        return new ConcurrentHashMap<>();
    }

    /**
     * 服务注册中心
     * @return
     */
    @Bean(name="serviceRegistry")
    public ServiceRegistry<Registration> smilodonServiceRegistry(){
        SmilodonServiceRegistry serviceRegistry = new SmilodonServiceRegistry();
        serviceRegistry.init();//init
        return serviceRegistry;
    }
}
