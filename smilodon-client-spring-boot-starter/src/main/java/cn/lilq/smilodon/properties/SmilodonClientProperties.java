package cn.lilq.smilodon.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:13
 * smilodon.client 客户端配置类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "smilodon.client")
public class SmilodonClientProperties {
    private Boolean registerWithSmilodon;//是否使用smilodon注册
    private Boolean fetchRegistry;//获取注册表-是否启用缓存.
    private String serviceUrl;//注册中心地址-url. 不为null
}
