package icu.redamancy.redamancyauthenticationcenter.service;

import com.google.gson.JsonObject;

/**
 * @Author redamancy
 * @Date 2022/5/5 17:44
 * @Version 1.0
 */
public interface UserAuthService {

    String Login(JsonObject jsonObject);

    boolean userIsExist(String onlyId);

    boolean signIn(JsonObject jsonObject, JsonObject resJson);
}
