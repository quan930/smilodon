package cn.lilq.smilodon;

import cn.lilq.smilodon.config.SmilodonClientConfig;
import cn.lilq.smilodon.config.SmilodonDisposableBean;
import cn.lilq.smilodon.config.SmilodonServiceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:50
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SmilodonClientConfig.class, SmilodonServiceConfig.class, SmilodonDisposableBean.class})
public @interface EnableSmilodonClient {
}
