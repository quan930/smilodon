package cn.lilq.smilodon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Li Liangquan
 * 2021/1/6 17:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer code;
    private String errorMsg;
    private Object data;
}
