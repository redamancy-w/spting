package icu.redamancy.cloud.controller;

import icu.redamancy.cloud.service.ORCService;
import icu.redamancy.common.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author redamancy
 * @Date 2022/6/6 16:26
 * @Version 1.0
 */
@ResponseResult
@RestController
public class ORCController {


    @Autowired
    private ORCService orcService;

    @PostMapping("/aliyunory")
    public Map<String, Object> Aliyunory(@RequestParam(name = "type", required = true) String type,
                                         @RequestParam(name = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String re = orcService.parsingIdCard(type, file);

        System.out.println(re);
        map.put("idInfo", re);
        return map;
    }


}
