package icu.redamancy.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"icu.redamancy.admin", "icu.redamancy.common"})
@EnableFeignClients(basePackages = "icu.redamancy.common.feignclient")
public class RedamancyAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedamancyAdminApplication.class, args);
    }
}
