package icu.redamancy.common.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author redamancy
 * @Date 2022/5/17 21:57
 * @Version 1.0
 */
@FeignClient("redamancy-staticresource-support")
public interface PictureClient {

    @PostMapping(value = "picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<Long> addPicture(@RequestParam(value = "objectName") String objectName, @RequestParam("userId") String userId, @RequestPart(value = "file") MultipartFile[] file);

    @PostMapping(value = "aliyunory",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE)
    String parsingIdCard(@RequestParam(value = "type") String type, @RequestPart(value = "file") MultipartFile file);

}
