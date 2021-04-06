package cn.lilq.smilodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 * @auther: Li Liangquan
 * @date: 2021/1/29 21:06
 */
@EnableSmilodonClient
@SpringBootApplication
public class SmilodonClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmilodonClientApplication.class,args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
