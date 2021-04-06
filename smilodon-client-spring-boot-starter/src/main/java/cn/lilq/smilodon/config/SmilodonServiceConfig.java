package cn.lilq.smilodon.config;

import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.client.SmilodonDiscoveryClient;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.service.SmilodonClientService;
import cn.lilq.smilodon.service.impl.SmilodonClientServiceImpl;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/8 19:05
 * service 装配配置类
 */
public class SmilodonServiceConfig {
    @Resource
    private SmilodonRegister smilodonRegister;
    @Resource
    private RestTemplate smilodonRestTemplate;
    @Resource
    private SmilodonClientProperties smilodonClientProperties;

    @Bean(name = "smilodonClientService")
    public SmilodonClientService smilodonClientService(){
        SmilodonClientServiceImpl smilodonClientService =  new SmilodonClientServiceImpl(smilodonRegister,smilodonRestTemplate,smilodonClientProperties);
        smilodonClientService.init();//初始化 发送注册信息
        return smilodonClientService;
    }

    @Bean(name = "discoveryClient")
    public DiscoveryClient discoveryClient(){
        return new SmilodonDiscoveryClient();
    }
}
