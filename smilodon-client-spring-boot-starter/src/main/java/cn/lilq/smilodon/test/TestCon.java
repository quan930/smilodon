package cn.lilq.smilodon.test;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.service.SmilodonClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:53
 */
@Controller
public class TestCon {
    @Resource
    private SmilodonRegister smilodonRegister;
    @Resource
    private SmilodonClientService smilodonClientService;

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Response home(){
        return new Response(200,"successful",smilodonRegister);
    }
}
