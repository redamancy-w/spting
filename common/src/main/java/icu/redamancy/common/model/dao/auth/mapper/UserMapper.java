package icu.redamancy.common.model.dao.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @Author redamancy
 * @Date 2022/5/6 14:35
 * @Version 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<EntityUser> {
}
