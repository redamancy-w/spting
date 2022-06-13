package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/7 13:58
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "unit")
public class Unit extends BaseEntity {

    @TableField(value = "unit")
    private Integer unit;
}
