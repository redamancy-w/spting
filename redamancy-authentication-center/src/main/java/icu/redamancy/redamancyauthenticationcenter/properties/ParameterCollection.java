package icu.redamancy.redamancyauthenticationcenter.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author redamancy
 * @Date 2022/5/6 19:58
 * @Version 1.0
 */
@Component
@Data
public class ParameterCollection {

    public static final String LOGIN_TYPE = "loginType";

    /**
     * 登录类型
     * 密码登录
     */
    public static final String PASSWORD = "password";

    /**
     * 登录类型
     * 微信授权登录
     */
    public static final String WX_LOGIN = "wx";

    /**
     * 微信登录授权类型
     * 授权吗类型
     */
    public static final String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 微信用户openid
     */
    public static final String OPENID = "openid";

    /**
     * 加密信息
     * 已无法获得用户地区，性别等私密信息
     */
    public static final String ENCRYPTEDDATA = "encryptedData";

    /**
     * 会话密钥，获得用户信息
     */
    public static final String SESSIONKEY = "session_key";

    /**
     * iv
     */
    public static final String IV = "iv";

    /**
     * 用户信息
     */
    public static final String USERINFO = "userInfo";

    /**
     * 微信用户头像
     */
    public static final String AVATARURL = "avatarUrl";

    public static final String NICKNAME = "nickName";

    /**
     * 权限范围
     */
    public static final String ALL = "all";
}
