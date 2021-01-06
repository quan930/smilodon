package cn.lilq.smilodon;

import cn.lilq.smilodon.config.SmilodonServerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 15:02
 * 启用注册发现服务端类
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SmilodonServerConfig.class})
public @interface EnableSmilodonServer {

}
