package cn.lilq.smilodon.service;

import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.SubscribeService;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 20:27
 */
public interface SmilodonClientService {
    /**
     * 注册实例
     * @param smilodonRegister 注册对象pojo
     * @return 注册成功 true 否则false
     */
    boolean register(SmilodonRegister smilodonRegister);

    /**
     * 取消注册实例
     * @param smilodonRegister 注册对象pojo
     * @return 取消注册成功 true 否则false
     */
    boolean unregister(SmilodonRegister smilodonRegister);

    /**
     * 订阅服务
     * @param subscribeService 订阅服务(客户端)
     * @return 订阅成功 true 否则false
     */
    boolean subscribe(SubscribeService subscribeService);

    /**
     * 取消订阅服务
     * @param subscribeService 订阅服务(客户端)
     * @return 订阅成功 true 否则false
     */
    boolean unsubscribe(SubscribeService subscribeService);
}
