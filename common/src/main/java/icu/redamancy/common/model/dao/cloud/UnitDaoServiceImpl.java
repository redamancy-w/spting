package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.UnitMapper;
import icu.redamancy.common.model.dao.cloud.service.UnitService;
import icu.redamancy.common.model.pojo.cloud.Unit;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/7 14:05
 * @Version 1.0
 */
@Service
public class UnitDaoServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {
}
