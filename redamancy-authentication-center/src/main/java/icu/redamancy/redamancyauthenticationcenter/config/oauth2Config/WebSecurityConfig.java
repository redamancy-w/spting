package icu.redamancy.redamancyauthenticationcenter.config.oauth2Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


//    protected void configure(AuthenticationManagerBuilder auth){
//        try {
//            auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("sss")).roles();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("账号密码设置错误");
//        }
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 允许匿名访问所有接口 主要是 oauth 接口     * @param http     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                放行
                .antMatchers("/oauth/**", "/WxApi/LoginByWx", "/WxApi/test")
                .permitAll()
//                剩下的请求都要求被认证
                .anyRequest()
                .authenticated()
                .and()
//                所有的表单请求被放行
                .formLogin()
                .permitAll()
//                csrf 跨域拦截
                .and()
                .csrf().disable();
    }
}
