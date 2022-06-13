package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.OrderMaterialsMapper;
import icu.redamancy.common.model.dao.cloud.service.OrderMaterialsService;
import icu.redamancy.common.model.pojo.cloud.OrderMaterials;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 18:56
 * @Version 1.0
 */
@Service
public class OrderMaterialsDaoServiceImpl extends ServiceImpl<OrderMaterialsMapper, OrderMaterials> implements OrderMaterialsService {
}
