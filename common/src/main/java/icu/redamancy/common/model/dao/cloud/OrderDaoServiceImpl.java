package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.OrderMapper;
import icu.redamancy.common.model.dao.cloud.service.OrderService;
import icu.redamancy.common.model.pojo.cloud.Order;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 18:51
 * @Version 1.0
 */
@Service
public class OrderDaoServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
