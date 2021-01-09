package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.service.SmilodonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * 注册实例
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
     * 取消注册实例
     * @param smilodonRegister smilodonRegister
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/unregister",method = RequestMethod.POST)
    public Response unregister(@RequestBody SmilodonRegister smilodonRegister){
        smilodonService.unregister(smilodonRegister);
        return new Response(200,"successful",null);
    }

    /**
     * 订阅服务
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/subscribe",method = RequestMethod.POST)
    public Response subscribeService(@RequestBody String url){
        smilodonService.subscribe(url);
        return new Response(200,"successful",null);
    }

    /**
     * 取消订阅服务
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/unsubscribe",method = RequestMethod.POST)
    public Response unsubscribeService(@RequestBody String url){
        smilodonService.unsubscribe(url);
        return new Response(200,"successful",null);
    }

    /**
     * 获取订阅服务列表
     * @return 订阅服务列表
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/subscribe",method = RequestMethod.GET)
    public Response subscribeServiceList(){
        return new Response(200,"successful",smilodonService.getSubscribeList());
    }

    /**
     * 获取注册服务id列表
     * @return 服务id列表
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/discovery",method = RequestMethod.GET)
    public Response discoveryList(){
        return new Response(200,"successful",smilodonService.getServices());
    }

    /**
     * 根据 服务id获取服务实例列表
     * @param id 服务id
     * @return 服务id对应的实例列表
     */
    @ResponseBody
    @RequestMapping(value="/smilodon/discovery/{id}", method= RequestMethod.GET)
    public Response getInstancesByServiceId(@PathVariable String id){
        return new Response(200,"successful",smilodonService.getInstancesByServiceId(id));
    }
}