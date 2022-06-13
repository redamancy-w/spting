package icu.redamancy.common.model.dao.picture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import icu.redamancy.common.model.pojo.picture.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author redamancy
 * @Date 2022/5/18 18:11
 * @Version 1.0
 */

@Mapper
public interface PictureMapper extends BaseMapper<Picture> {
}
