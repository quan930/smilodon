package cn.lilq.smilodon.service;

import cn.lilq.smilodon.SmilodonRegister;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 20:27
 */
public interface SmilodonClientService {
    /**
     * 注册
     * @param smilodonRegister 注册对象pojo
     * @return 注册成功 true 否则false
     */
    boolean register(SmilodonRegister smilodonRegister);
}
