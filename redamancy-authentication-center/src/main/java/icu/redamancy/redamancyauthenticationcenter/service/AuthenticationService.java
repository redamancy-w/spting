package icu.redamancy.redamancyauthenticationcenter.service;

/**
 * @Author redamancy
 * @Date 2022/5/6 19:36
 * @Version 1.0
 */
public interface AuthenticationService {

    /**
     * 根据登录类型去选择不同的登录方式
     */
    String toLogin();
}
