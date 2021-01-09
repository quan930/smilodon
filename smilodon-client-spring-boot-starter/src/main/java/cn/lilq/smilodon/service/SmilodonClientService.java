package cn.lilq.smilodon.service;

import cn.lilq.smilodon.SmilodonRegister;

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
     * 订阅服务
     * @param url 订阅服务的url(客户端)
     * @return 订阅成功 true 否则false
     */
    boolean subscribe(String url);

    /**
     * 取消订阅服务
     * @param url 订阅服务的url(客户端)
     * @return 订阅成功 true 否则false
     */
    boolean unsubscribe(String url);
}
