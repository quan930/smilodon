package cn.lilq.smilodon.service.impl;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.SmilodonRegistration;
import cn.lilq.smilodon.SubscribeService;
import cn.lilq.smilodon.properties.SmilodonServerProperties;
import cn.lilq.smilodon.service.SmilodonService;
import cn.lilq.smilodon.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
    @Resource
    private List<SubscribeService> subscribeServiceList;

    @Override
    public String getFixedDelay() {
        return (smilodonServerProperties.getTestingTime() * 1000)+"";
    }

    @Override
    public boolean checkRegistration(Registration registration) {
//        log.debug(registration.getInstanceId()+"---check url:"+registration.getUri()+"/actuator/health");
        try{
            Status status = restTemplate.getForObject(registration.getUri()+"/actuator/health",Status.class);
            assert status != null;
            //设置健康状态
            serviceRegistry.setStatus(registration,status.getStatus());
        }catch (RestClientException e){
            log.debug(registration.getInstanceId()+"---check url:"+registration.getUri()+"/actuator/health"+"  "+registration.getInstanceId()+"error");
            serviceRegistry.setStatus(registration,"error");
        }
        //获得错误次数
        String count = registration.getMetadata().get("count");
//        log.info(registration.getInstanceId()+"error count"+count);
        if (Integer.parseInt(count)>=maxCheckCount()){
            log.info(registration.getInstanceId()+"---check url:"+registration.getUri()+"/actuator/health  "+registration.getInstanceId()+"error max--remove");
            serviceRegistry.deregister(registration);
            //广播
            radio(registration,false);
            return true;
        }
        return false;
    }

    @Override
    public int maxCheckCount() {
        return smilodonServerProperties.getMaxWaitTime()/smilodonServerProperties.getTestingTime();
    }

    @Override
    public Map<String, List<Registration>> getServiceRegistryMap() {
        return this.serviceRegistryMap;
    }

    @Override
    public List<String> getServices() {
        return new ArrayList<>(serviceRegistryMap.keySet());
    }

    @Override
    public List<SmilodonRegister> getInstancesByServiceId(String serviceId) {
        return Util.ListRegistrationToListListSmilodonRegister(serviceRegistryMap.get(serviceId));
    }

    @Override
    public void register(SmilodonRegister smilodonRegister) {
        Registration registration = new SmilodonRegistration(smilodonRegister);
        log.info("注册-服务id:"+registration.getServiceId()+"--实例id:"+registration.getInstanceId());
        serviceRegistry.register(registration);
        //广播
        radio(smilodonRegister,true);
    }

    @Override
    public void unregister(SmilodonRegister smilodonRegister) {
        Registration registration = new SmilodonRegistration(smilodonRegister);
        log.info("取消注册-服务id:"+registration.getServiceId()+"--实例id:"+registration.getInstanceId());
        serviceRegistry.deregister(registration);
        //广播
        radio(smilodonRegister,false);
    }

    @Override
    public void subscribe(SubscribeService subscribeService) {
        log.info("subscribe: "+subscribeService);
        subscribeServiceList.add(subscribeService);
    }

    @Override
    public void unsubscribe(SubscribeService subscribeService) {
        log.info("unsubscribe: "+subscribeService);
        subscribeServiceList.remove(subscribeService);
    }

    @Override
    public List<SubscribeService> getSubscribeList() {
        return subscribeServiceList;
    }

    @Override
    public Map<String, List<SmilodonRegister>> getServiceRegistryTable() {
        Map<String, List<SmilodonRegister>> map = new HashMap<>();
        serviceRegistryMap.keySet().forEach(serviceID -> {
            List<SmilodonRegister> smilodonRegisterList = new ArrayList<>();
            serviceRegistryMap.get(serviceID).forEach(registration -> smilodonRegisterList.add(new SmilodonRegister(registration.getServiceId(),registration.getInstanceId(),registration.getHost(),registration.getPort(),registration.isSecure())));
            map.put(serviceID,smilodonRegisterList);
        });
        return map;
    }

    private boolean radio(Registration registration, boolean isAdd) {
        AtomicBoolean b = new AtomicBoolean(true);
        subscribeServiceList.forEach(subscribeService -> {
            try{
                if(isAdd){//增加
                    Response response = restTemplate.postForObject(subscribeService.getSubscribeServiceUrl()+"/smilodon/client/register",
                            new SmilodonRegister(registration.getServiceId(),registration.getInstanceId(),registration.getHost(),registration.getPort(),registration.isSecure()), Response.class);
                    assert response != null;
                    if (response.getCode()==200){
                        log.info("radio "+isAdd+" "+subscribeService.getSubscribeServiceUrl()+" successful");
                    }
                }else {//移除
                    Response response = restTemplate.postForObject(subscribeService.getSubscribeServiceUrl()+"/smilodon/client/unregister",
                            new SmilodonRegister(registration.getServiceId(),registration.getInstanceId(),registration.getHost(),registration.getPort(),registration.isSecure()), Response.class);
                    assert response != null;
                    if (response.getCode()==200){
                        log.info("radio "+isAdd+" "+subscribeService.getSubscribeServiceUrl()+" successful");
                    }
                }
            }catch (RestClientException e){
//            e.printStackTrace();
                b.set(false);
            }
        });
        return b.get();
    }

    private boolean radio(SmilodonRegister smilodonRegister, boolean isAdd) {
        AtomicBoolean b = new AtomicBoolean(true);
        subscribeServiceList.forEach(subscribeService -> {
            try{
                if(isAdd){//增加
                    Response response = restTemplate.postForObject(subscribeService.getSubscribeServiceUrl()+"/smilodon/client/register", smilodonRegister, Response.class);
                    assert response != null;
                    if (response.getCode()==200){
                        log.info("radio "+isAdd+" "+subscribeService.getSubscribeServiceUrl()+" successful");
                    }
                }else {//移除
                    Response response = restTemplate.postForObject(subscribeService.getSubscribeServiceUrl()+"/smilodon/client/unregister",smilodonRegister, Response.class);
                    assert response != null;
                    if (response.getCode()==200){
                        log.info("radio "+isAdd+" "+subscribeService.getSubscribeServiceUrl()+" successful");
                    }
                }
            }catch (RestClientException e){
//            e.printStackTrace();
                b.set(false);
            }
        });
        return b.get();
    }

    private static class Status{
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
