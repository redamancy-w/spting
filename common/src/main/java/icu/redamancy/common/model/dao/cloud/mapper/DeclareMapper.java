package icu.redamancy.common.model.dao.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.redamancy.common.model.BaseEntity;
import icu.redamancy.common.model.pojo.cloud.EntityDeclare;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author redamancy
 * @Date 2022/6/7 10:06
 * @Version 1.0
 */
@Mapper
public interface DeclareMapper extends BaseMapper<EntityDeclare> {
}
