package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.MaterialsMapper;
import icu.redamancy.common.model.dao.cloud.service.MaterialsService;
import icu.redamancy.common.model.pojo.cloud.Materials;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/10 01:36
 * @Version 1.0
 */

@Service
public class MaterialsDaoServiceImpl extends ServiceImpl<MaterialsMapper, Materials> implements MaterialsService {
}
