package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.serviceregistry.SmilodonRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:09
 * Smilodon Controller
 */

@Slf4j
@Controller
public class SmilodonCon {
    @Resource
    private ServiceRegistry<Registration> serviceRegistry;
    @Resource
    private Map<String, List<Registration>> serviceRegistryMap;

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Response home(){
//        if (serviceRegistryMap.get("SERVER-BOOK")==null){
//            log.info("空");
//        }else {
//            log.info(serviceRegistryMap.get("SERVER-BOOK")+"");
//        }
        return new Response(200,"successful",serviceRegistryMap);
    }

    /**
     * 注册
     * @param smilodonRegister smilodonRegister
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/register",method = RequestMethod.POST)
    public Response addRegister(@RequestBody SmilodonRegister smilodonRegister){
        Registration registration = new SmilodonRegistration(smilodonRegister);
        log.info("服务id"+registration.getServiceId());
        log.info("实例id"+registration.getInstanceId());
        serviceRegistry.register(registration);
        log.info(serviceRegistry+"");
        return new Response(200,"successful",null);
    }
}