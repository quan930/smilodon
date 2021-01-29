package cn.lilq.smilodon.util;

import cn.lilq.smilodon.SmilodonRegister;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.util.ArrayList;
import java.util.List;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/7 22:49
 * 工具类
 */
public class Util {
    /**
     * 格式化 List-Registration 转 List-SmilodonRegister
     * @param registrations List-Registration
     * @return List-SmilodonRegister (pojo)
     */
    public static List<SmilodonRegister> ListRegistrationToListListSmilodonRegister(List<Registration> registrations){
        List<SmilodonRegister> smilodonRegisters = new ArrayList<>();
        if (registrations==null)
            return smilodonRegisters;

        registrations.forEach(registration -> {
            smilodonRegisters.add(new SmilodonRegister(registration.getServiceId(),registration.getInstanceId(),registration.getHost(),registration.getPort(),registration.isSecure()));
        });
        return smilodonRegisters;
    }
}
