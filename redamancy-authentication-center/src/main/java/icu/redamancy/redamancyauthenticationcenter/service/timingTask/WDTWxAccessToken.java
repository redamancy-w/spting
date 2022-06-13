package icu.redamancy.redamancyauthenticationcenter.service.timingTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import icu.redamancy.redamancyauthenticationcenter.properties.WxApiProperties;
import icu.redamancy.redamancyauthenticationcenter.service.feign.wxapi.MyWxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WDTWxAccessToken {

    private static final String GRANT_TYPE = "client_credential";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String ERRCODE = "errcode";

    @Resource
    private MyWxApi wxApi;

    @Resource
    private WxApiProperties wxApiProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Scheduled(fixedDelay = (1000*60)*(55+60))
    public void getAccessToken(){

        String token =  wxApi.getAccessToken(GRANT_TYPE,wxApiProperties.getAppid(),wxApiProperties.getAppsercret());

        JsonObject jsonObject = JsonParser.parseString(token).getAsJsonObject();

        assert jsonObject.get(ERRCODE).getAsInt() != 0 : jsonObject.get(EXPIRES_IN).getAsString();

        String error = jsonObject.get(EXPIRES_IN).getAsString();
        JsonElement jsonelement = jsonObject.get(ACCESS_TOKEN);
        String accessToken = jsonelement.getAsString();
        assert accessToken != null : "accessTokn is null";

        long exTime = jsonObject.get(EXPIRES_IN).getAsLong();
        redisTemplate.opsForValue().set("test:wx:accessToken",accessToken,exTime, TimeUnit.SECONDS);
    }

}
