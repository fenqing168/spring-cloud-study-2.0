# Zoopeeper服务注册与发现

## Eurekat停止更新如何选型 

### 停更说明

* [github 说明](https://github.com/Netflix/eureka/wiki)
* ![](../images/img31.png)

## SpringCloud整合Zookeeper代替Eureka

### 注册中心Zookeeper 

* zookeeper是一个分布式协调工具，可以实现注册中心功能
* 关闭Linux服务器防火墙自动Zookeeper服务器
* Zookeeper服务器取代Eureka服务器，zk作为服务注册中心

### 服务提供者 

#### 新建cloud-provider-payment8004 

#### POM 

* 其他的与Eureka项目一致，只需要将eureka更换成zookeeper-starter

* ```xml
  <!-- zookeeper 的spring cloud依赖 -->
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
  </dependency>
  ```

#### YML 

* ```yaml
  server:
    port: 8004
  
  spring:
    application:
      name: cloud-provider-payment
    cloud:
      zookeeper:
        connect-string: 192.168.31.200:2181
  ```

#### 主启动类 

*  @EnableDiscoveryClient注解是Spring cloud官方定义的开启服务发现的注解

* zookeeper-starter遵循了Spring cloud 的相关规范，使用@EnableDiscoveryClient可以开启服务注册与发现功能

* ```java
  @SpringBootApplication
  @EnableDiscoveryClient
  public class PaymentApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(PaymentApplication.class, args);
      }
  
  }
  ```

#### Controller  

* ```java
  @RestController
  @Slf4j
  public class PaymentController {
  
      @Value("${server.port}")
      private String port;
  
      @PostMapping("/payment/zk")
      public String insert() {
          return "spring cloud with zookeeper" + port + "\t" + UUID.randomUUID().toString();
      }
  
  
  }
  ```

#### 启动8004注册到zookeeper

#### 验证测试 

* 启动后发现zookeeper出现了新的节点
* ![](../images/img32.png)

#### 验证测试2 

* 对zookeeper的json进行分析

* ```json
  {
      "name":"cloud-provider-payment",
      "id":"d253959d-e7ae-4e4d-bdb0-6fa813f7f6c3",
      "address":"DESKTOP-FAJOUBO",
      "port":8004,
      "sslPort":null,
      "payload":{
          "@class":"org.springframework.cloud.zookeeper.discovery.ZookeeperInstance",
          "id":"application-1",
          "name":"cloud-provider-payment",
          "metadata":{
              "instance_status":"UP"
          }
      },
      "registrationTimeUTC":1615655298583,
      "serviceType":"DYNAMIC",
      "uriSpec":{
          "parts":[
              {
                  "value":"scheme",
                  "variable":true
              },
              {
                  "value":"://",
                  "variable":false
              },
              {
                  "value":"address",
                  "variable":true
              },
              {
                  "value":":",
                  "variable":false
              },
              {
                  "value":"port",
                  "variable":true
              }
          ]
      }
  }
  ```

#### 思考

* zookeeper服务节点分临时节点与持久节点
* 服务节点是临时节点还是持久节点
  * 关闭服务后，并没有立刻删除节点，但是一段时间后删除了节点
  * 所以zookeeper做注册中心，使用的是临时节点

### 服务消费者

#### 新建cloud-consumerzk-order80 

#### POM 

* 与cloud-consumer-order80，eureka相关依赖换成zookeeper依赖

#### YML 

* ```yaml
  server:
    port: 80
    
  spring:
    application:
      name: cloud-consumer-order
    cloud:
      zookeeper:
        connect-string: 192.168.31.200:2181
  ```

#### 主启动 

* ```java
  @SpringBootApplication
  @EnableDiscoveryClient
  public class OrderApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(OrderApplication.class, args);
      }
  
  }
  
  ```

#### 业务类 

* ```java
  @Configuration
  public class ApplicationContextConfig {
  
      @Bean
      @LoadBalanced
      public RestTemplate getRestTemplate(){
          return new RestTemplate();
      }
  
  }
  
  ```

* ```java
  @RestController
  public class OrderZkController {
  
      @Autowired
      private RestTemplate restTemplate;
  
      private static final String INVOKE_URL = "http://cloud-provider-payment";
  
      @GetMapping("/consumer/payment/zk")
      public String paymentInfo(){
          return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
      }
  
  }
  ```

#### 验证测试 

* ![](../images/img33.png)

#### 访问测试