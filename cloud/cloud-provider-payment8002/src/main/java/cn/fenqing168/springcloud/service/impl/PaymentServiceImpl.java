package cn.fenqing168.springcloud.service.impl;

import cn.fenqing168.springcloud.dao.PaymentDao;
import cn.fenqing168.springcloud.entities.Payment;
import cn.fenqing168.springcloud.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int insert(Payment payment) {
        return paymentDao.insert(payment);
    }

    @Override
    public Payment getById(Long id) {
        return paymentDao.getById(id);
    }
}
