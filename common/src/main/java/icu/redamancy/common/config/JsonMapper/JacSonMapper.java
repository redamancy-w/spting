package icu.redamancy.common.config.JsonMapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author redamancy
 * @Date 2022/6/7 15:02
 * @Version 1.0
 */
@Configuration
public class JacSonMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
