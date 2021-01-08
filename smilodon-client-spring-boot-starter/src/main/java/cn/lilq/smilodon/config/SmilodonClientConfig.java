package cn.lilq.smilodon.config;

import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.properties.SmilodonInstanceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:32
 * smilodon 客户端配置类
 * ok
 */

@Slf4j
@EnableConfigurationProperties({SmilodonInstanceProperties.class, SmilodonClientProperties.class})//ioc注入
public class SmilodonClientConfig {
    @Value("${server.port}")
    private Integer port;
    @Value("${spring.application.name}")
    private String applicationName;
    @Resource
    private SmilodonInstanceProperties smilodonInstanceProperties;
    @Resource
    private SmilodonClientProperties smilodonClientProperties;

    /**
     * 注册信息 bean
     * @return SmilodonRegister
     */
    @Bean(name = "smilodonRegister")
    public SmilodonRegister smilodonRegister(){
        SmilodonRegister smilodonRegister = new SmilodonRegister();
        if (smilodonInstanceProperties.getPreferIpAddress()){
            try {
                InetAddress ip4 = Inet4Address.getLocalHost();
                log.info("ip:"+ip4.getHostAddress());
                smilodonRegister.setHost(ip4.getHostAddress());
                return new SmilodonRegister(applicationName.toUpperCase(),smilodonInstanceProperties.getInstanceId(),ip4.getHostAddress(),port,false);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }else {
            return new SmilodonRegister(applicationName.toUpperCase(),smilodonInstanceProperties.getInstanceId(),smilodonInstanceProperties.getHostname(),port,false);
        }
        return null;
    }

    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
