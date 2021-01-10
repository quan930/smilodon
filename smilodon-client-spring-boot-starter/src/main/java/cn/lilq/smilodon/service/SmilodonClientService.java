package cn.lilq.smilodon.service;

import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.SubscribeService;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

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

    /**
     * 获取服务列表
     * @return 获取服务id列表 不存在size为0 失败null
     */
    List<String> getServices();

    /**
     * 根据服务id 获取服务实例列表
     * @param serviceId 服务id
     * @return 获取服务实例列表 不存在size为0 失败null
     */
    List<ServiceInstance> getInstances(String serviceId);
}
