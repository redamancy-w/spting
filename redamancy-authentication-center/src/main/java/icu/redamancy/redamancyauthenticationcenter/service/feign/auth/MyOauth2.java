package icu.redamancy.redamancyauthenticationcenter.service.feign.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author redamancy
 * @Date 2022/5/9 20:47
 * @Version 1.0
 */
@FeignClient(value = "redamancy-authentication-center")
public interface MyOauth2 {
    @PostMapping(value ="/oauth/token")
    public String auth(@RequestParam("grant_type")String grantType,
                       @RequestParam("username")String code,
                       @RequestParam("password")String password,
                       @RequestParam("scope")String scope
//                       @RequestHeader("Authorization") String token
    );

    @GetMapping(value = "/WxApi/test")
    public String test();
}
