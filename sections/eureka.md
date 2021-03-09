# eureka

## 基础知识

### 什么是服务治理

* Spring cloud 封装了Netflix公司开发的Eureka模块来实现服务治理
* 在传统的rpc远程调用框架中，管理每个服务与服务之间的依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务与服务之间的依赖关系，可以实现服务调用，负载均衡，容错等，实现服务发现与注册

### 什么是服务注册

* **eureka采用了cs的设计架构，Eureka server 作为服务注册功能的服务器，他是服务注册中心。而系统中的其他微服务，使用Eureka的客户端链接到Eureka server并维持心跳连接。这样系统的维护人员就可以通过Eureka server 来监控系统中各个微服务是否正常运行**
* **在服务注册与发现中，有一个注册中心，当服务器启动的时候，会吧当前自己的服务器的详细，比如服务地址通讯地址等以别名方式注册到注册中心。另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用。RPC远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系（服务治理概念）。在任何RPC远程框架中。都会有一个注册中心（存放服务地址相关信息（接口地址））**

* ![](../images/img20.png)

## Eureka的两个组件：Eureka Server和Eureka Client

### Eureka Server 提供服务注册

* **各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表将会存储所有可用服务节点信息，服务节点的信息可以在界面中直观看到**

### EurekaClient通过注册中心进行访问

是一个Java客户端，用于简化EurekaServer的交互，客户端同时也具备一个内置的，使用轮询（round-robin）负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（周期是30秒）。如果Eureka Server 在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）

## 单机Eureka搭建

### 创建EurekaServer注册中心工程

#### 建Model 

* 创建maven项目cloud-eureka-server7001

#### 改POM 

* eureka的1.x与2.x的区别

  * 老版本采用spring-cloud-starter-eureka
  * 新版本采用spring-cloud-start-netflix-eureka-server（对server与client做了细分）

* 由于Spring boot采用2.2.13.RELEASE版本，在Spring官方提供的依赖关系查询中得知，可以使用**Hoxton.SR10**的版本

* ![](../images/img21.png)

* 引入在父工程补充Hoxton.SR10的Spring cloud 依赖仲裁工程

* ```xml
  <spring.cloud.version>Hoxton.SR10</spring.cloud.version>
  ```

* ```xml
  <!-- spring cloud 依赖管理仲裁项目-->
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring.cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
  </dependency>
  ```

* 在cloud-eureka-server7001项目中添加基本web环境的同时加上eureka-server的starter

* ```xml
  <dependencies>
      <!--spring boot web 的自动配置依赖-->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <!-- spring boot 监控系统健康 -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-actuator</artifactId>
      </dependency>
  
      <!-- spring boot 单元测试 -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
      </dependency>
  
      <!-- eureka server 组件 -->
      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
      </dependency>
  
  </dependencies>
  ```

#### 写YML 

* ```yaml
  server:
    port: 7001
  
  eureka:
    instance:
      hostname: localhost # eureka服务端的实例名称
  
    client:
      # 是否将自己注册到注册中心
      register-with-eureka: false
      # 是否是否需要去检索服务，由于当前是注册中心，不需要去服务发现与调用
      fetch-registry: false
      # 是一个Map<String, String>，defaultZone可以指定交互的地址查询服务和注册服务的地址
      service-url:
        # 提供暴露的地址，客户端通过该地址来连接并且注册
        defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  ```

#### 主启动 

* ```java
  /**
   * 通过EnableEurekaServer来开启注册中心服务功能
   * @author Administrator
   */
  @SpringBootApplication
  @EnableEurekaServer
  public class EurekaApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(EurekaApplication7001.class, args);
      }
  
  }
  ```

* 

#### 测试

### EurekaClient端payment8001注册进EurekaServer成为服务提供者

### EurekaClient端Order80注册进EurekaServer成为服务消费者