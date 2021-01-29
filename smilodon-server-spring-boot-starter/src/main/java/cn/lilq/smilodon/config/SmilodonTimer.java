package cn.lilq.smilodon.config;

import cn.lilq.smilodon.service.SmilodonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/7 17:39
 * 定时器
 */
@Slf4j
@EnableScheduling
public class SmilodonTimer {
    @Resource
    private SmilodonService smilodonService;
    @Resource
    private Map<String, List<Registration>> serviceRegistryMap;

    /**
     * 定时器init 初始等待时间30s
     */
    @Scheduled(fixedDelayString = "#{smilodonService.getFixedDelay()}",initialDelay=30000)
    public void timer() {
        /*
         * 遍历map
         * 检查健康状态
         */
        for(String key : serviceRegistryMap.keySet()){
            log.info("key:"+key);
            List<Registration> list = serviceRegistryMap.get(key);
            list.forEach(registration -> {
                smilodonService.checkRegistration(registration);
            });
        }
    }
}
