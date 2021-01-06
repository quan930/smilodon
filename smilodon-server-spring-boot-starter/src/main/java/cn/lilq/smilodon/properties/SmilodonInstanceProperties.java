package cn.lilq.smilodon.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 14:16
 * smilodon实例配置类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "smilodon.instance")
public class SmilodonInstanceProperties {
    private String hostname;
    private Boolean preferIpAddress;
}
