package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.Materials;
import icu.redamancy.common.model.pojo.cloud.OrderMaterials;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/12 02:31
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
public class OrderMaterialsBo extends OrderMaterials {

    private Materials materials;

    private String filePath;

}
