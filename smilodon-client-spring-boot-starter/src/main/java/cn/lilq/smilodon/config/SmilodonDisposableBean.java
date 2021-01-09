package cn.lilq.smilodon.config;

import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.properties.SmilodonClientProperties;
import cn.lilq.smilodon.service.SmilodonClientService;
import cn.lilq.smilodon.util.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.Resource;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/9 19:15
 * springboot 关闭事件
 */
@Slf4j
public class SmilodonDisposableBean implements DisposableBean {
    @Resource
    private SmilodonRegister smilodonRegister;
    @Resource
    private SmilodonClientService smilodonClientService;
    @Resource
    private SmilodonClientProperties smilodonClientProperties;

    @Override
    public void destroy() throws Exception {
        //判断是否需要取消注册
        if (smilodonClientProperties.getRegisterWithSmilodon()){
            log.info("unregister");
            smilodonClientService.unregister(smilodonRegister);
        }
        //取消订阅
        log.info("unsubscribe");
        smilodonClientService.unsubscribe(ClientUtil.smilodonRegisterToUri(smilodonRegister));
    }
}
