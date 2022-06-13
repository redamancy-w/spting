package icu.redamancy.common.serviceinterface.staticresource;

import icu.redamancy.common.model.pojo.picture.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/5/16 14:06
 * @Version 1.0
 */
public interface PictureManageService {

    List<Picture> upload(String objectName, String userId, MultipartFile[] file);

    List<Long> addPictureToDS(List<Picture> pictureList);

    String parsing(String type, MultipartFile file);

}
