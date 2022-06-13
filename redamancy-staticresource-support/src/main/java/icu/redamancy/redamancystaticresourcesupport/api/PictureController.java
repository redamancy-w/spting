package icu.redamancy.redamancystaticresourcesupport.api;

import icu.redamancy.common.model.pojo.picture.Picture;
import icu.redamancy.common.serviceinterface.staticresource.PictureManageService;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.result.ResponseResult;
import icu.redamancy.common.utils.result.ResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/5/17 10:13
 * @Version 1.0
 */
@RestController
public class PictureController {

    @Resource
    private PictureManageService pictureManageService;

    //    向服务器中添加图片，并加入数据库中，返回id列表
    @PostMapping("/picture")
    public List<Long> addPicture(String objectName, String userId, MultipartFile[] file) {

        assert objectName.equals("") && userId.equals("") : "服务器发生意想不到意外,请联系管理员";

        List<Picture> imagesList = null;
        List<Long> idList = null;
        if (file != null) {
            imagesList = pictureManageService.upload(objectName, userId, file);
            idList = pictureManageService.addPictureToDS(imagesList);
        }
        return idList;
    }

    //    解析身份证，并将内容返回至前端
    @PostMapping("/aliyunory")
    public String parsingIdCard(String type, MultipartFile file) {

        if (file == null) {
            throw new BaseException(ResultCode.FEEDBACK.code(), "处理异常");
        }

        return pictureManageService.parsing(type, file);
    }


}
