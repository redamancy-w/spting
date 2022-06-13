package icu.redamancy.common.model.dao.picture;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.redamancy.common.model.dao.picture.mapper.PictureMapper;
import icu.redamancy.common.model.dao.picture.service.PictureService;
import icu.redamancy.common.model.pojo.picture.Picture;
import org.springframework.stereotype.Repository;

/**
 * @Author redamancy
 * @Date 2022/5/18 18:33
 * @Version 1.0
 */

@Repository
public class PictureDaoServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
}
