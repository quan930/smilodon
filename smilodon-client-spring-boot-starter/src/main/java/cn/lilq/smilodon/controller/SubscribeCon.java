package cn.lilq.smilodon.controller;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.service.SmilodonClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/11 19:28
 * 订阅 controller 缓存注册表时起作用 服务端广播
 */
@Controller
public class SubscribeCon {
    @Resource
    private SmilodonClientService smilodonClientService;

    /**
     * 注册表 增加实例
     * @param smilodonRegister smilodonRegister
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/client/register",method = RequestMethod.POST)
    public Response addRegister(@RequestBody SmilodonRegister smilodonRegister){
        if (smilodonClientService.addRegister(smilodonRegister)){
            return new Response(200,"successful",null);
        }
        return new Response(500,"error",null);
    }


    /**
     * 注册表 remove实例
     * @param smilodonRegister smilodonRegister
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/smilodon/client/unregister",method = RequestMethod.POST)
    public Response unregister(@RequestBody SmilodonRegister smilodonRegister){
        if (smilodonClientService.removeRegister(smilodonRegister)){
            return new Response(200,"successful",null);
        }
        return new Response(500,"error",null);
    }
}
