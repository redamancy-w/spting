package icu.redamancy.cloud.service;

import icu.redamancy.common.feignclient.PictureClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author redamancy
 * @Date 2022/6/6 16:43
 * @Version 1.0
 */

@Service
public class ORCService {
    
    @Autowired
    private PictureClient pictureClient;

    public String parsingIdCard(String type, MultipartFile file) {

        return pictureClient.parsingIdCard(type, file);
    }
}
