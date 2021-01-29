package cn.lilq.smilodon.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:03
 * smilodon.instance 实例配置类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "smilodon.instance")
public class SmilodonInstanceProperties {
    private String hostname;//主机地址
    //是否使用ip显示 是- hostname设置ip 否hostname不为null
    private Boolean preferIpAddress;
    private String instanceId;//实例id
}

