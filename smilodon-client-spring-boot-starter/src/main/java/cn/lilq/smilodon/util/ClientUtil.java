package cn.lilq.smilodon.util;

import cn.lilq.smilodon.Response;
import cn.lilq.smilodon.SmilodonRegister;
import cn.lilq.smilodon.SmilodonRegistration;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/9 18:15
 */
public class ClientUtil {
    public static String smilodonRegisterToUri(SmilodonRegister smilodonRegister) {
        String scheme = smilodonRegister.getSecure() ? "https" : "http";
        return String.format("%s://%s:%s", scheme,smilodonRegister.getHost(), smilodonRegister.getPort());
    }

    /**
     * object 转 List<ServiceInstance>
     * @param object Object
     * @return List<ServiceInstance>
     */
    public static List<ServiceInstance> objectToListServiceInstance(Object object){
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        List<SmilodonRegister> list = JSON.parseArray(JSON.toJSONString(object),SmilodonRegister.class);
        list.forEach(smilodonRegister -> {
            serviceInstances.add(new SmilodonRegistration(smilodonRegister));
        });
        return serviceInstances;
    }

    /**
     * object 转 Map<String, List<SmilodonRegister>>
     * @param object
     * @return Map<String, List<SmilodonRegister>> 服务注册表
     */
    public static Map<String, List<SmilodonRegister>> objectToMapStringListSmilodonRegister(Object object){
        Map<String, List<SmilodonRegister>> map = JSON.parseObject(JSON.toJSONString(object), new TypeReference<Map<String, List<SmilodonRegister>>>() {});
        return map;
    }
}
