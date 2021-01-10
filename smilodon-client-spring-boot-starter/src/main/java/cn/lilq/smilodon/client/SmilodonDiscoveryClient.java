package cn.lilq.smilodon.client;

import cn.lilq.smilodon.service.SmilodonClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/10 16:48
 */

@Slf4j
public class SmilodonDiscoveryClient implements DiscoveryClient {
    @Resource
    private SmilodonClientService smilodonClientService;


    public SmilodonDiscoveryClient(){
        log.info("装配成功");
    }

    /**
     * 描述
     * @return description
     */
    @Override
    public String description() {
        return "Smilodon Discovery Client";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        return smilodonClientService.getInstances(serviceId);
    }

    @Override
    public List<String> getServices() {
        return smilodonClientService.getServices();
    }
}
