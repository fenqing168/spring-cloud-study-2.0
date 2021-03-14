package cn.fenqing168.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 */
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
