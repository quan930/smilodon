package cn.lilq.smilodon.service.impl;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.SubscribeService;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.service.SmilodonClientService;
import cn.lilq.smilodon.util.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 20:28
 */
@Slf4j
public class SmilodonClientServiceImpl implements SmilodonClientService {
    private SmilodonRegister smilodonRegister;
    private RestTemplate restTemplate;
    private SmilodonClientProperties smilodonClientProperties;
    private Boolean cache=false;//是否缓存注册表
    private Map<String, List<SmilodonRegister>>  serviceregistry;//服务注册表

    public SmilodonClientServiceImpl() {
    }

    public SmilodonClientServiceImpl(SmilodonRegister smilodonRegister, RestTemplate restTemplate, SmilodonClientProperties smilodonClientProperties) {
        this.smilodonRegister = smilodonRegister;
        this.restTemplate = restTemplate;
        this.smilodonClientProperties = smilodonClientProperties;
    }

    public void init(){
        log.info("服务启动-init"+smilodonRegister.getServiceId());
        //订阅
        subscribe(new SubscribeService(ClientUtil.smilodonRegisterToUri(smilodonRegister),smilodonClientProperties.getFetchRegistry()));
        //注册实例
        if (smilodonClientProperties.getRegisterWithSmilodon()){
            register(smilodonRegister);
        }
        /**
         * 判断是否需要实例化注册表
         */
        cache = smilodonClientProperties.getFetchRegistry();
        if (cache){
            log.info("缓存注册表");
            try{
                Response response = restTemplate.getForObject(smilodonClientProperties.getServiceUrl()+"smilodon/serviceregistry", Response.class);
                assert response != null;
                if (response.getCode()==200){
                    serviceregistry = ClientUtil.objectToMapStringListSmilodonRegister(response.getData());
                    log.info("缓存注册表"+serviceregistry);
                    cache = true;
                }else {
                    cache = false;
                }
            }catch (RestClientException e){
//            e.printStackTrace();
                cache=false;
            }
        }else {
            log.info("不缓存注册表");
        }
    }

    @Override
    public boolean register(SmilodonRegister smilodonRegister) {
        log.info("注册uri:"+smilodonClientProperties.getServiceUrl()+"/smilodon/register"+"  ---"+smilodonRegister);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/register",smilodonRegister, Response.class);
            assert response != null;
            if (response.getCode()==200){
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("注册error");
        return false;
    }

    @Override
    public boolean unregister(SmilodonRegister smilodonRegister) {
        log.info("取消注册uri:"+smilodonClientProperties.getServiceUrl()+"/smilodon/unregister"+"  ---"+smilodonRegister);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/unregister",smilodonRegister, Response.class);
            assert response != null;
            if (response.getCode()==200){
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("取消注册error");
        return false;
    }

    @Override
    public boolean subscribe(SubscribeService subscribeService) {
        log.info("订阅uri:"+subscribeService);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/subscribe",subscribeService, Response.class);
            assert response != null;
            if (response.getCode()==200){
                log.info("订阅successful");
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("订阅error");
        return false;
    }

    @Override
    public boolean unsubscribe(SubscribeService subscribeService) {
        log.info("unsubscribe uri: "+subscribeService);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/unsubscribe",subscribeService, Response.class);
            assert response != null;
            if (response.getCode()==200){
                log.info("unsubscribe successful");
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("unsubscribe error");
        return false;
    }

    @Override
    public List<String> getServices() {
        log.info("getServices cache:"+cache);
        if (cache){
            //缓存
            return new ArrayList<>(serviceregistry.keySet());
        }else {
            try{
                Response response = restTemplate.getForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/discovery", Response.class);
                assert response != null;
                if (response.getCode()==200){
                    return (List<String>)response.getData();
                }
            }catch (RestClientException e){
//            e.printStackTrace();
            }
            log.info("getServices error");
        }
        return null;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        log.info("getInstances "+serviceId+" cache:"+cache);
        if (cache){
            //缓存
            return ClientUtil.objectToListServiceInstance(serviceregistry.get(serviceId));
        }else {
            try{
                Response response = restTemplate.getForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/discovery/"+serviceId, Response.class);
                assert response != null;
                if (response.getCode()==200){
                    return ClientUtil.objectToListServiceInstance(response.getData());
                }
            }catch (RestClientException e){
//            e.printStackTrace();
            }
            log.info("getInstances error");
        }
        return null;
    }

    @Override
    public boolean addRegister(SmilodonRegister smilodonRegister) {
        if (cache){
            serviceregistry.computeIfAbsent(smilodonRegister.getServiceId(), k -> new ArrayList<>());
            serviceregistry.get(smilodonRegister.getServiceId()).remove(smilodonRegister);//防止重复
            serviceregistry.get(smilodonRegister.getServiceId()).add(smilodonRegister);
        }
        return cache;
    }

    @Override
    public boolean removeRegister(SmilodonRegister smilodonRegister) {
        if (cache){
            serviceregistry.get(smilodonRegister.getServiceId()).remove(smilodonRegister);
            if (serviceregistry.get(smilodonRegister.getServiceId()).size()==0)
                serviceregistry.remove(smilodonRegister.getServiceId());
        }
        return cache;
    }
}
