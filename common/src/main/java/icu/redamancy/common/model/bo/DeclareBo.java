package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.EntityDeclare;
import icu.redamancy.common.model.pojo.picture.Picture;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/7 11:05
 * @Version 1.0
 */
@Setter
@Getter
public class DeclareBo extends EntityDeclare {
    private List<Picture> imageList;
}
