package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.StayMapper;
import icu.redamancy.common.model.dao.cloud.service.StayService;
import icu.redamancy.common.model.pojo.cloud.Stay;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/8 21:42
 * @Version 1.0
 */
@Service
public class StayDaoServiceImpl extends ServiceImpl<StayMapper, Stay> implements StayService {
}
