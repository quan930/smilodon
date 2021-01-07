package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.service.SmilodonService;
import cn.lilq.smilodon.serviceregistry.SmilodonRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private SmilodonService smilodonService;

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Response home(){
//        if (serviceRegistryMap.get("SERVER-BOOK")==null){
//            log.info("空");
//        }else {
//            log.info(serviceRegistryMap.get("SERVER-BOOK")+"");
//        }
        return new Response(200,"successful",smilodonService.getServiceRegistryMap());
    }

    /**
     * 注册
     * @param smilodonRegister smilodonRegister
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/register",method = RequestMethod.POST)
    public Response addRegister(@RequestBody SmilodonRegister smilodonRegister){
//        Registration registration = new SmilodonRegistration(smilodonRegister);
//        log.info("注册-服务id:"+registration.getServiceId()+"--实例id:"+registration.getInstanceId());
//        smilodonService.getServiceRegistry().register(registration);
        smilodonService.register(smilodonRegister);
        return new Response(200,"successful",null);
    }


    /**
     * 获取注册服务id列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/discovery",method = RequestMethod.GET)
    public Response discoveryList(){
        return new Response(200,"successful",smilodonService.getServices());
    }

    /**
     * 根据 服务id获取服务实例列表
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smilodon/discovery/{id}", method= RequestMethod.GET)
    public Response getInstancesByServiceId(@PathVariable String id){
        return new Response(200,"successful",smilodonService.getInstancesByServiceId(id));
    }
}