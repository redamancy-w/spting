package icu.redamancy.common.config.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Author redamancy
 * @Date 2022/6/11 02:50
 * @Version 1.0
 */

//@EnableWebMvc
@Configuration
public class ResponseDataConvertConfig implements WebMvcConfigurer {

//            @Bean
//            @ConditionalOnMissingBean
//            public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
//
//                CustomMappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new CustomMappingJackson2HttpMessageConverter();
//                ObjectMapper objectMapper = new ObjectMapper();
//                SimpleModule simpleModule = new SimpleModule();
//                // 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
//                simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
//                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//                objectMapper.registerModule(simpleModule);
//                jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//                return jackson2HttpMessageConverter;
//            }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        // 坑2
        // converters.add(jackson2HttpMessageConverter);
        converters.add(0, jackson2HttpMessageConverter);
    }
}
