package cn.lilq.smilodon.util;

import cn.lilq.smilodon.SmilodonRegister;

import java.net.URI;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/9 18:15
 */
public class ClientUtil {
    public static String smilodonRegisterToUri(SmilodonRegister smilodonRegister) {
        String scheme = smilodonRegister.getSecure() ? "https" : "http";
        return String.format("%s://%s:%s", scheme,smilodonRegister.getHost(), smilodonRegister.getPort());
    }
}
