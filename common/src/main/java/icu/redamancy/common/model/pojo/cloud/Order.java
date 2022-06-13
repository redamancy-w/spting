package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/10 13:57
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "myorder")
public class Order extends BaseEntity {

    @TableField(value = "housenumberid")
    private Long housenumberid;

    @TableField(value = "userid")
    private Long userid;

    @TableField(value = "materialsListId")
    private String materialsListId;

    @TableField(value = "state")
    private Integer state;

}
