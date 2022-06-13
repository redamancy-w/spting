package icu.redamancy.common.config.response;

import lombok.extern.log4j.Log4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * @Author redamancy
 * @Date 2022/6/11 03:01
 * @Version 1.0
 */

public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter{

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否可以将 Java 对象转换成返回内容.
     * 匹配web api(形如/web/xxxx)中的接口方法的返回参数
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (super.canWrite(clazz, mediaType)) {
            ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (ra != null) { // web请求
                HttpServletRequest request = ra.getRequest();
                String uri = request.getRequestURI(); // 例如: "/web/frontApplicationPage"
                if (uri.startsWith("/**")) {
                    return true;
                }
            }
        }
        return false;
    }
}
