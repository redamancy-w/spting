package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.MaterialsTitleMapper;
import icu.redamancy.common.model.dao.cloud.service.MaterialsService;
import icu.redamancy.common.model.dao.cloud.service.MaterialsTitleService;
import icu.redamancy.common.model.pojo.cloud.MaterialsTitle;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:38
 * @Version 1.0
 */
@Service
public class MaterialsTitleDaoServiceImpl extends ServiceImpl<MaterialsTitleMapper, MaterialsTitle> implements MaterialsTitleService {
}
