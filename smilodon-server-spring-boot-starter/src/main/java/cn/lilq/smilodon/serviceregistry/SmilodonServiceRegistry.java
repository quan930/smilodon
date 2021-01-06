package cn.lilq.smilodon.serviceregistry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:40
 * 服务注册中心
 */
@Slf4j
public class SmilodonServiceRegistry implements ServiceRegistry<Registration> {
    /**
     * key 注册列表中的所有服务
     * value 所有实例
     * 注册表
     */
    @Resource
    private Map<String, List<Registration>> serviceRegistryMap;
//    //注册中心状态类
//    @Resource
//    private Status status;

    /**
     * 初始化方法
     */
    public void init(){
//        serviceRegistryMap=new ConcurrentHashMap<>();
        //启动定时器
    }

    /**
     * 注册
     * @param registration
     */
    @Override
    public void register(Registration registration) {
        List<Registration> list = serviceRegistryMap.get(registration.getServiceId());

        if (serviceRegistryMap.get(registration.getServiceId())==null){
            serviceRegistryMap.put(registration.getServiceId(),new CopyOnWriteArrayList<>(new Registration[]{registration}));
        }else {
            serviceRegistryMap.get(registration.getServiceId()).add(registration);
        }
        log.info("注册一条记录");
        log.info(serviceRegistryMap.get(registration.getServiceId()).get(0).getServiceId());
    }

    /**
     * deregister
     * @param registration
     */
    @Override
    public void deregister(Registration registration) {
        serviceRegistryMap.get(registration.getServiceId()).removeIf(
                registration1 -> registration1.getInstanceId().equals(registration1.getInstanceId())
        );
        log.info("删除一条记录");
    }

    /**
     *
     */
    @Override
    public void close() {

    }

    /**
     * 设置实例状态
     * @param registration 单个实例
     * @param status 状态
     * 1.获得服务名列表
     * 2.获得单个实例
     * 3.设置计数器
     */
    @Override
    public void setStatus(Registration registration, String status) {
        serviceRegistryMap.get(registration.getServiceId()).stream()
                .filter(registration1 -> registration.getInstanceId().equals(registration1.getInstanceId()))
                .findFirst()
                .ifPresent(registration1 -> {
                    if (status.equals("up")){
                        registration1.getMetadata().put("count","0");
                    }else {
                        if (registration1.getMetadata().get("count")==null){
                            registration1.getMetadata().put("count","1");
                        }else {
                            registration1.getMetadata().put("count",String.valueOf(Integer.parseInt(registration1.getMetadata().get("count"))+1));
                        }
                    }
                    registration1.getMetadata().put(new Date()+"",status);
                });
    }

    /**
     * 获得状态
     * @param registration 单个实例
     * @return 返回健康状态 "0"良好 非零为心跳错误次数
     */
    @Override
    public String getStatus(Registration registration) {
        AtomicReference<String> count = new AtomicReference<>();
        serviceRegistryMap.get(registration.getServiceId()).stream()
                .filter(registration1 -> registration.getInstanceId().equals(registration1.getInstanceId()))
                .findFirst()
                .ifPresent(registration1 -> {
                    if (registration.getMetadata().get("count")==null)
                        count.set("0");
                    else {
                        count.set(registration.getMetadata().get("count"));
                    }
                });
        return count.get();
    }
}
