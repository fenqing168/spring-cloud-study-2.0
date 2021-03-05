package cn.fenqing168.springcloud.service;

import cn.fenqing168.springcloud.entities.Payment;

/**
 * @author Administrator
 */
public interface IPaymentService {

    /**
     * 添加
     * @param payment 实体
     * @return 影响条数
     */
    int insert(Payment payment);

    /**
     * 查询
     * @param id id
     * @return 查询到的结果
     */
    Payment getById(Long id);

}
