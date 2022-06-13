package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.HouseNumberMapper;
import icu.redamancy.common.model.dao.cloud.service.HouseNumberService;
import icu.redamancy.common.model.pojo.cloud.HouseNumber;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/7 14:17
 * @Version 1.0
 */
@Service
public class HouseNumberDaoServiceImpl extends ServiceImpl<HouseNumberMapper, HouseNumber> implements HouseNumberService {
}
