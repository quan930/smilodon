package cn.lilq.smilodon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Li Liangquan
 * 2021/1/10 16:12
 * 订阅服务注册类 pojo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeService {
    private String subscribeServiceUrl;//订阅服务url
    private Boolean cache;//订阅服务是否缓存注册表
}
