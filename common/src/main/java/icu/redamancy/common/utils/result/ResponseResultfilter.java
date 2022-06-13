package icu.redamancy.common.utils.result;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器
@Configuration
public class ResponseResultfilter implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseResultInterceptor())
                //配置拦截的路径，这里所有路径都会拦截
                .addPathPatterns("/**");
        //排除拦截
        //.excludePathPatterns()
    }

}
