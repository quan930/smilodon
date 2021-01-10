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

import java.util.List;

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
        log.info("取消订阅uri:"+subscribeService);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/unsubscribe",subscribeService, Response.class);
            assert response != null;
            if (response.getCode()==200){
                log.info("取消订阅successful");
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("取消订阅 error");
        return false;
    }

    @Override
    public List<String> getServices() {
        if (cache){
            //缓存
        }else {
            log.info("getServices");
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
        if (cache){
            //缓存
        }else {
            log.info("getInstances"+serviceId);
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
}
