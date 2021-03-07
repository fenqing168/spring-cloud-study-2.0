# RestTemplate

## 是什么

* > RestTemplate提供了多种便捷访问远程Http服务的方法
  >
  > 是一种简单便捷的访问restful服务模块类，是Spring提供的用于访问Rest服务的**客户端模板工具集**

### 官网

* [RestTemplate官网](https://docs.spring.io/spring-framework/docs/5.3.x/javadoc-api/org/springframework/web/client/RestTemplate.html)

### 使用

* 使用RestTemplate访问restful接口非常简单粗暴无脑

  (url, requestMap. responseBean.class)这三个参数分别代表Rest请求地址，请求参数，http相应转换被转换的对象类型