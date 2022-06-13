package icu.redamancy.common.model.pojo.picture;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author redamancy
 * @Date 2022/5/18 17:57
 * @Version 1.0
 */

@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "picture")
public class Picture extends BaseEntity {

    @TableField(value = "filename")
    private String filename;

    @TableField(value = "filepath")
    private String filepath;

    @TableField(value = "userid")
    private Long userId;

    @TableField(value = "objectId")
    private String objectName;

    @TableField(value = "picturedescribe")
    private String picturedescribe;
}