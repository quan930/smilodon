# smilodon
![release:0.2.2](https://img.shields.io/badge/release-0.2.2-blue)
![Spring Boot Version:2.3.0.RELEASE](https://img.shields.io/badge/Spring%20Boot%20Version-2.3.0.RELEASE-brightgreen)
![Spring Cloud Version:Hoxton.SR8](https://img.shields.io/badge/Spring%20Cloud%20Version-Hoxton.SR8-brightgreen)
![last-commit](https://img.shields.io/github/last-commit/quan930/smilodon)


### 简介
smilodon，是一套服务注册发现工具包，实现的目的是更好了解微服务的原理，实现`spring-cloud-commons`部分功能，尚不支持`@LoadBalanced`注解

### smilodon服务器

##### 配置smilodon服务器
+ 先决条件
    + JDK 1.8或更高
    + springboot 2.3或更高
    + springcloud Hoxton.SR8或更高
+ maven 依赖
    ```xml
    <dependency>
        <groupId>cn.lilq.smilodon</groupId>
        <artifactId>smilodon-server-spring-boot-starter</artifactId>
        <version>0.2.4</version>
    </dependency>
    ```
##### 构建smilodon服务器
+ 启用smilodon服务器
    ```java
    @EnableSmilodonServer
    @SpringBootApplication
    public class SmilodonServerApplication {
        public static void main(String[] args) {
            SpringApplication.run(SmilodonServerApplication.class,args);
        }
    }
    ```
+ 可选配置
    ```properties
    smilodon.instance.prefer-ip-address=true
    smilodon.instance.hostname=127.0.0.1
    smilodon.server.max-wait-time=60
    smilodon.server.testing-time=20
    ```

### smilodon客户端
##### 配置smilodon客户端
+ 先决条件
    + JDK 1.8或更高
    + springboot 2.3或更高
    + springcloud Hoxton.SR8或更高
+ maven 依赖
    ```xml
    <dependency>
        <groupId>cn.lilq.smilodon</groupId>
        <artifactId>smilodon-client-spring-boot-starter</artifactId>
        <version>0.2.4</version>
    </dependency>
    ```
##### 构建smilodon客户端
+ 启用smilodon服务器
  ```java
  @EnableSmilodonClient
  @SpringBootApplication
  public class SmilodonClientApplication {
      public static void main(String[] args) {
          SpringApplication.run(SmilodonClientApplication.class,args);
      }
  }
  ```
+ 可选配置
    ```properties
    smilodon.instance.prefer-ip-address=true
    smilodon.instance.instance-id=ordersys5001
    smilodon.client.fetch-registry=false
    #smilodon.client.fetch-registry=true
    smilodon.client.service-url=http://localhost:7010
    #smilodon.client.register-with-smilodon=false
    smilodon.client.register-with-smilodon=true
    ```
+ 使用`discoveryClient`进行服务发现
    ```java
    @Resource
    private DiscoveryClient discoveryClient;
    
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public void getServices(){
        System.out.println(discoveryClient.getServices());
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public void getInstances(@PathVariable String id){
        System.out.println(discoveryClient.getInstances(id));
    }
    ```
### 演示
+ [test地址](https://github.com/quan930/smilodon/tree/main/smilodon-test)
### 不足
+ 动态注入controller
+ 心跳检测
+ 订阅controller 动态注入  (注入异常)