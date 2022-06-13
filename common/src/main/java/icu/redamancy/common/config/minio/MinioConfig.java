package icu.redamancy.common.config.minio;

import icu.redamancy.common.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author redamancy
 * @Date 2022/5/15 21:12
 * @Version 1.0
 */
@Configuration
public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient;
        try {
            minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpoint())
                    .credentials(minioProperties.getAccesskey(), minioProperties.getSecretkey())
                    .build();
        } catch (Exception e) {
            e.getMessage();
            minioClient = null;
        }
        return minioClient;

    }
}
