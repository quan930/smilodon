package cn.lilq.smilodon;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/29 21:00
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSmilodonServer
@SpringBootApplication
public class SmilodonServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmilodonServerApplication.class,args);
    }
}
