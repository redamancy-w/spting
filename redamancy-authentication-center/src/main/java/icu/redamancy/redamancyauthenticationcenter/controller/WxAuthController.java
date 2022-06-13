package icu.redamancy.redamancyauthenticationcenter.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import icu.redamancy.common.model.dao.auth.UserDaoServiceImpl;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.handlerequest.HandlerParameter;
import icu.redamancy.common.utils.result.ResponseResult;
import icu.redamancy.common.utils.result.Result;
import icu.redamancy.common.utils.result.ResultCode;
import icu.redamancy.common.utils.result.ResultResponse;
import icu.redamancy.redamancyauthenticationcenter.service.AuthenticationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author redamancy
 * @Date 2022/5/5 16:32
 * @Version 1.0
 */
@ResponseResult
@RestController
@RequestMapping("WxApi")
public class WxAuthController {

    @Resource(name = "authenticationServiceImpl")
    private AuthenticationService authenticationService;

    @PostMapping("/LoginByWx")
    public Map<String, Object> LoginByWx(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String token = authenticationService.toLogin();
        if (token.equals("")) {
            throw new BaseException(ResultCode.PARAMETER_EXCEPTION.code(), "登录失败");
        }
        map.put("token", token);

        return map;
    }

    @Resource
    private UserDaoServiceImpl userDaoService;

    @PostMapping("registered")
    public Result registered(EntityUser user){

        if (!userDaoService.lambdaQuery().eq(EntityUser::getUsername,user.getUsername()).exists()){
            userDaoService.save(user);
        }

        return Result.one("msg","注册成功").success();

    }

    @GetMapping("/test")
    public Map<String, Object> test() {
        throw new BaseException(ResultCode.BAD_REQUEST);
    }

}
