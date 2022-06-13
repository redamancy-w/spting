package icu.redamancy.common.model.bo;

import icu.redamancy.common.model.pojo.cloud.Materials;
import icu.redamancy.common.model.pojo.cloud.MaterialsTitle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:33
 * @Version 1.0
 */

@Getter
@Setter
public class MaterialsBO extends MaterialsTitle {
    private List<MaterialsImageBO> materialsBO;
}
