package icu.redamancy.redamancyauthenticationcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"icu.redamancy.redamancyauthenticationcenter",
        "icu.redamancy.common.model",
        "icu.redamancy.common.fill",
        "icu.redamancy.common.utils",
        "icu.redamancy.common.config.mybatis",
        "icu.redamancy.common.config.openfeign"})
@EnableScheduling
@EnableFeignClients
@MapperScan("icu.redamancy")
public class RedamancyAuthenticationCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedamancyAuthenticationCenterApplication.class, args);

    }

}
