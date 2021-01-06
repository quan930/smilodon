package cn.lilq.smilodon.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 16:51
 * 注册中心状态pojo类
 */

@Data
public class Status {
    private String status;//健康状态
    private Date lastCheckTime;//最后检查时间
}
