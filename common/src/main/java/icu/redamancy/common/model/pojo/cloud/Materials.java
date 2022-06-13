package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:27
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "materials")
public class Materials extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "price")
    private Integer price;

    @TableField(value = "number")
    private Integer number;

    //售出数量
    @TableField(value = "soldNumber")
    private Integer soldNumber;

    @TableField(value = "limitNumber")
    private Integer limitNumber;

    @TableField(value = "imageList")
    private String imageList;

    @TableField(value = "titleId")
    private Long titleId;
}
