package cn.lilq.smilodon;

import lombok.*;

/**
 * Li Liangquan
 * 2021/1/6 17:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Response {
    private Integer code;
    private String errorMsg;
    private Object data;
}
