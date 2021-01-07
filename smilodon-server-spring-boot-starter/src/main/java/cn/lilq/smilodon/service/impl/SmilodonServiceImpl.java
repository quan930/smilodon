package cn.lilq.smilodon.service.impl;

import cn.lilq.smilodon.properties.SmilodonInstanceProperties;
import cn.lilq.smilodon.properties.SmilodonServerProperties;
import cn.lilq.smilodon.service.SmilodonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 23:37
 * OK 不要更改
 */

@Slf4j
public class SmilodonServiceImpl implements SmilodonService {
    @Resource
    private SmilodonServerProperties smilodonServerProperties;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ServiceRegistry<Registration> serviceRegistry;
    @Resource
    private Map<String, List<Registration>> serviceRegistryMap;

    @Override
    public String getFixedDelay() {
        return (smilodonServerProperties.getTestingTime() * 1000)+"";
    }

    @Override
    public void checkRegistration(Registration registration) {
        log.debug(registration.getInstanceId()+"---check url:"+registration.getUri()+"/actuator/health");
        try{
            Status status = restTemplate.getForObject(registration.getUri()+"/actuator/health",Status.class);
            assert status != null;
            //设置健康状态
            serviceRegistry.setStatus(registration,status.getStatus());
        }catch (RestClientException e){
            log.debug(registration.getInstanceId()+"error");
            serviceRegistry.setStatus(registration,"error");
        }
        //获得错误次数
        String count = registration.getMetadata().get("count");
        log.info(registration.getInstanceId()+"error count"+count);
        if (Integer.parseInt(count)>=maxCheckCount()){
            serviceRegistry.deregister(registration);
            log.info(registration.getInstanceId()+"error max--remove");
        }
    }

    @Override
    public int maxCheckCount() {
        return smilodonServerProperties.getMaxWaitTime()/smilodonServerProperties.getTestingTime();
    }

    @Override
    public ServiceRegistry<Registration> getServiceRegistry() {
        return this.serviceRegistry;
    }

    @Override
    public Map<String, List<Registration>> getServiceRegistryMap() {
        return this.serviceRegistryMap;
    }

    private class Status{
        private String status;

        public Status() {
        }

        public Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Status status1 = (Status) o;
            return Objects.equals(status, status1.status);
        }

        @Override
        public int hashCode() {
            return Objects.hash(status);
        }

        @Override
        public String toString() {
            return "Status{" +
                    "status='" + status + '\'' +
                    '}';
        }
    }
}