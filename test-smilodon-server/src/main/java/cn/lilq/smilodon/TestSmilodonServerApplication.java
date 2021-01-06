package cn.lilq.smilodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: Li Liangquan
 * @date: 2021/1/6 18:48
 */
@EnableSmilodonServer
@SpringBootApplication
public class TestSmilodonServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSmilodonServerApplication.class,args);
    }
}
