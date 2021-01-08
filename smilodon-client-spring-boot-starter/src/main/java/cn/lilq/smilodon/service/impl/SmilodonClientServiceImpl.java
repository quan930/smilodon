package cn.lilq.smilodon.service.impl;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.service.SmilodonClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
        register(smilodonRegister);
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
}
