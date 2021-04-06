package cn.lilq.smilodon;

import lombok.*;

/**
 * Li Liangquan
 * 2021/1/6 15:27
 * 公共注册类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SmilodonRegister {
    private String serviceId;//服务名称
    private String instanceId;//实例id
    private String host;//服务主机
    private Integer port;//服务端口号
    private Boolean secure;//服务是否https
}
