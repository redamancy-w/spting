package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/6/7 09:58
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "cloud_declare")
public class EntityDeclare extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "sex")
    private int sex;

    @TableField(value = "addresses")
    private String addresses;

    @TableField(value = "idcard")
    private String idcard;

    @TableField(value = "pictureList")
    private String pictureList;

    @TableField(value = "relation")
    private String relation;

    @TableField(value = "housenumberid")
    private Long housenumberid;

    @TableField(value = "userid")
    private Long userid;

    @TableField(value = "state")
    private Integer state;

}
