package cn.fenqing168.springcloud.dao;

import cn.fenqing168.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
@Mapper
public interface PaymentDao {

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
    Payment getById(@Param("id") Long id);

}
