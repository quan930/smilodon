package cn.lilq.smilodon.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/6 14:18
 * smilodon服务端配置类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "smilodon.server")
public class SmilodonServerProperties {
    private Integer testingTime;
    private Integer maxWaitTime;
}
