package cn.fenqing168.springcloud.controller;

import cn.fenqing168.springcloud.commons.BaseResult;
import cn.fenqing168.springcloud.entities.Payment;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 */
@RestController
@Slf4j
public class OrderController {

    public final static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    public RestTemplate restTemplate;

    @PostMapping("/consumer/payment/create")
    public BaseResult<Integer> create(@RequestBody Payment payment){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(payment), headers);
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", formEntity, BaseResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public BaseResult<Payment> get(@PathVariable Long id){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, BaseResult.class);
    }
}
