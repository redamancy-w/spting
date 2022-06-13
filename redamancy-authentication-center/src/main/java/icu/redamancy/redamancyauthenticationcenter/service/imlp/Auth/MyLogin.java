package icu.redamancy.redamancyauthenticationcenter.service.imlp.Auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import icu.redamancy.redamancyauthenticationcenter.properties.ParameterCollection;
import icu.redamancy.redamancyauthenticationcenter.properties.WxApiProperties;
import icu.redamancy.redamancyauthenticationcenter.service.UserAuthService;
import icu.redamancy.redamancyauthenticationcenter.service.feign.wxapi.MyWxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author redamancy
 * @Date 2022/5/6 13:57
 * @Version 1.0
 */

@Slf4j
@Component
public class MyLogin {

    private static final String AUTHORIZATION_CODE = "authorization_code";

    @Resource
    private MyWxApi wxApi;

    @Resource
    private WxApiProperties wxApiProperties;

    @Resource( name = "wxUserAuthServiceImpl")
    private UserAuthService WxUserAuthServiceImpl;

    public String WxLogin(JsonObject jsonObject){

        String res =  wxApi.getOpenid(wxApiProperties.getAppid(),wxApiProperties.getAppsercret(),jsonObject.get("code").getAsString(),AUTHORIZATION_CODE);

        if (res.equals("")) {
            log.error("微信接口访问失败");
            return null;
        }
        JsonObject resJson = JsonParser.parseString(res).getAsJsonObject();

        boolean userIsExist = WxUserAuthServiceImpl.userIsExist(resJson.get(ParameterCollection.OPENID).getAsString());
        if (!userIsExist){
            try{
                WxUserAuthServiceImpl.signIn(jsonObject,resJson);
                boolean userIsExist2 = WxUserAuthServiceImpl.userIsExist(resJson.get(ParameterCollection.OPENID).getAsString());

                if (!userIsExist2){
                    log.error("注册失败");
                    return null;
                }

            }catch (Exception e){
                log.error("注册失败");
            }
        }

        return WxUserAuthServiceImpl.Login(resJson);
    }
}
