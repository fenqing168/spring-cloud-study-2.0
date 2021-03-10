package cn.fenqing168.springcloud.controller;

import cn.fenqing168.springcloud.commons.BaseResult;
import cn.fenqing168.springcloud.entities.Payment;
import cn.fenqing168.springcloud.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private IPaymentService iPaymentService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String port;

    /**
     * 添加
     *
     * @param payment 实体
     * @return 影响条数
     */
    @PostMapping("/payment/create")
    public BaseResult<Integer> insert(@RequestBody Payment payment) {
        int res = iPaymentService.insert(payment);
        log.info("****插入结果：{}", res);
        if (res > 0) {
            return BaseResult.success(null, "插入数据库成功");
        } else {
            return BaseResult.error(null, "插入数据库失败");
        }
    }

    /**
     * 查询
     *
     * @param id id
     * @return 查询到的结果
     */
    @GetMapping("/payment/get/{id}")
    public BaseResult<Payment> getById(@PathVariable("id") Long id) {
        Payment payment = iPaymentService.getById(id);
        if (payment != null) {
            return BaseResult.success(payment, "查询成功：" + port);
        } else {
            return BaseResult.error(null, "查询失败");
        }
    }

    @GetMapping("/payment/discovery")
    public BaseResult<Object> discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*************elemnet:{}", service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("{}\t{}\t{}\t{}", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }
        return BaseResult.success(discoveryClient);
    }

}
