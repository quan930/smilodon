package cn.lilq.smilodon.service;

import cn.lilq.smilodon.SmilodonRegister;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import java.util.List;
import java.util.Map;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 23:36
 */
public interface SmilodonService {
    /**
     * 获得定时器间隔时间
     * @return 毫秒
     */
    String getFixedDelay();

    /**
     * 检查登记
     * @param registration 登记对象
     * @return 需要移除返回true 否则返回false
     */
    void checkRegistration(Registration registration);

    /**
     * 最大检测计数
     * @return 计数
     */
    int maxCheckCount();


//    /**
//     * 获得注册中心
//     * @return ServiceRegistry<Registration>
//     */
//    ServiceRegistry<Registration> getServiceRegistry();


    /**
     * 获得注册中心的注册表
     * @return Map<String, List<Registration>>
     */
    Map<String, List<Registration>> getServiceRegistryMap();


    /**
     * 获取服务列表
     * @return List<String>
     */
    List<String> getServices();


    /**
     * 根据服务id获取可用实例-pojo list
     * @param serviceId 服务id
     * @return 实例list pojo
     */
    List<SmilodonRegister> getInstancesByServiceId(String serviceId);

    /**
     * 注册服务
     * @param smilodonRegister 服务pojo
     */
    void register(SmilodonRegister smilodonRegister);
}
