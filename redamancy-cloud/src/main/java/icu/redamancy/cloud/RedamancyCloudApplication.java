package icu.redamancy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author ${USER}
 * @Date ${DATE} ${TIME}
 * @Version 1.0
 */



@EnableScheduling
@SpringBootApplication(scanBasePackages = {"icu.redamancy.cloud", "icu.redamancy.common"})
@EnableFeignClients(basePackages = "icu.redamancy.common.feignclient")
public class RedamancyCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedamancyCloudApplication.class, args);
    }
}