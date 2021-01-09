package cn.lilq.smilodon.service.impl;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.service.SmilodonClientService;
import cn.lilq.smilodon.util.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 20:28
 */
@Slf4j
public class SmilodonClientServiceImpl implements SmilodonClientService {
    private SmilodonRegister smilodonRegister;
    private RestTemplate restTemplate;
    private SmilodonClientProperties smilodonClientProperties;

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
        subscribe(ClientUtil.smilodonRegisterToUri(smilodonRegister));
        //注册实例
        if (smilodonClientProperties.getRegisterWithSmilodon()){
            register(smilodonRegister);
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
    public boolean subscribe(String url) {
        log.info("订阅uri:"+url);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/subscribe",url, Response.class);
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
    public boolean unsubscribe(String url) {
        log.info("取消订阅uri:"+url);
        try{
            Response response = restTemplate.postForObject(smilodonClientProperties.getServiceUrl()+"/smilodon/unsubscribe",url, Response.class);
            assert response != null;
            if (response.getCode()==200){
                log.info("取消订阅successful");
                return true;
            }
        }catch (RestClientException e){
//            e.printStackTrace();
        }
        log.info("取消订阅error");
        return false;
    }
}
