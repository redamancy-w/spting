package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.Materials;
import icu.redamancy.common.model.pojo.picture.Picture;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:44
 * @Version 1.0
 */
@Setter
@Getter
public class MaterialsImageBO extends Materials {

    private String filePath;
}
