package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/10 18:48
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "order_materialsList")
public class OrderMaterials extends BaseEntity {

    @TableField(value = "housenumberid")
    private Long housenumberid;

    @TableField(value = "materialsid")
    private Long materialsid;

    @TableField(value = "amount")
    private Integer amount;

}
