package icu.redamancy.redamancyauthenticationcenter.service.imlp;

import com.google.gson.JsonObject;
import icu.redamancy.common.utils.handlerequest.HandlerParameter;
import icu.redamancy.redamancyauthenticationcenter.properties.ParameterCollection;
import icu.redamancy.redamancyauthenticationcenter.service.AuthenticationService;
import icu.redamancy.redamancyauthenticationcenter.service.imlp.Auth.MyLogin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author redamancy
 * @Date 2022/5/6 19:42
 * @Version 1.0
 */

@Service("authenticationServiceImpl")
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String CODE = "code";

    @Resource
    private MyLogin myLogin;

    @Override
    public String toLogin() {

        JsonObject jsonObject = HandlerParameter.getHandlerParameter().getJSONParamBody();

        String loginType = jsonObject.get(ParameterCollection.LOGIN_TYPE).getAsString();

        switch (loginType) {
            case ParameterCollection.WX_LOGIN:
                return   myLogin.WxLogin(jsonObject);
            case ParameterCollection.PASSWORD:
                break;
            default:
        }
        return null;
    }
}
