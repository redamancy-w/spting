package icu.redamancy.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author redamancy
 * @Date 2022/5/15 21:11
 * @Version 1.0
 */

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String endpoint;
    private String accesskey;
    private String secretkey;
    private String bucketname;
}
