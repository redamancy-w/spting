package icu.redamancy.common.model.dao.cloud;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.cloud.mapper.DeclareMapper;
import icu.redamancy.common.model.dao.cloud.service.DeclareService;
import icu.redamancy.common.model.dao.picture.mapper.PictureMapper;
import icu.redamancy.common.model.dao.picture.service.PictureService;
import icu.redamancy.common.model.pojo.cloud.EntityDeclare;
import icu.redamancy.common.model.pojo.picture.Picture;
import org.springframework.stereotype.Service;

/**
 * @Author redamancy
 * @Date 2022/6/7 10:09
 * @Version 1.0
 */


@Service
public class DeclareDaoServiceImpl extends ServiceImpl<DeclareMapper, EntityDeclare> implements DeclareService {
}
