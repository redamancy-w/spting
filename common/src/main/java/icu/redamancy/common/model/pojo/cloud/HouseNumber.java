package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/7 14:00
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "housenumber")
public class HouseNumber extends BaseEntity {

    @TableField(value = "housenumber")
    private String housNumber;

    @TableField(value = "unitId")
    private Long unit;
    
}
