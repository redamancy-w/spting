package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.Order;
import icu.redamancy.common.model.pojo.cloud.OrderMaterials;
import lombok.Data;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/11 23:34
 * @Version 1.0
 */
@Data
public class OrderBO extends Order {

    private List<OrderMaterialsBo> materialsBOList;

    private UserBO userBO;

}
