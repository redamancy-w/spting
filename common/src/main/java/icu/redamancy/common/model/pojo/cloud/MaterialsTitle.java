package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:25
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "materialsTitle")
public class MaterialsTitle extends BaseEntity {

    @TableField(value = "title")
    private String title;

}
