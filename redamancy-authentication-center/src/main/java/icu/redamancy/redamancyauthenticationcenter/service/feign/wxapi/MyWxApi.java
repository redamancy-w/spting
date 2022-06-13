package icu.redamancy.redamancyauthenticationcenter.service.feign.wxapi;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "WX-API",url="https://api.weixin.qq.com")
public interface MyWxApi {

//    获得AccessToken
    @GetMapping(value="/cgi-bin/token")
    String getAccessToken(@RequestParam("grant_type")String grantType,
                          @RequestParam("appid")String appid,
                          @RequestParam("secret")String secret);

//获得用户openid 和sessionkey
    @GetMapping(value="/sns/jscode2session")
    String getOpenid(@RequestParam("appid")String appid,
                       @RequestParam("secret")String secret,
                       @RequestParam("js_code")String code,
                       @RequestParam("grant_type")String grantType);

//    获取用户手机号
    @GetMapping(value="/wxa/business/getuserphonenumber")
    String getUserPhoneNumber(@RequestParam("access_token")String accessToken,
                              @RequestParam("code") String code);
}
