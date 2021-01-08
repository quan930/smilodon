package cn.lilq.smilodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/8 16:58
 */

@EnableSmilodonClient
@SpringBootApplication
public class TestServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestServerApplication.class,args);
    }
}
