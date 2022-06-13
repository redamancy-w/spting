package icu.redamancy.common.model.pojo.cloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import icu.redamancy.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author redamancy
 * @Date 2022/6/8 21:38
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName(value = "stay")
public class Stay extends BaseEntity {

    @TableField(value = "userId")
    private Long userId;

    @TableField(value = "housenumberid")
    private Long housenumberid;

    @TableField(value =  "population")
    private String population;
}
