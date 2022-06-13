package icu.redamancy.redamancystaticresourcesupport.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author redamancy
 * @Date 2022/6/5 17:08
 * @Version 1.0
 */
@Configuration
public class AliyunConfig {

    @Value("${AIYUN_ORC.accesskeyid}")
    private String accesskeyid;

    @Value("${AIYUN_ORC.accesskeysecret}")
    private String accesskeyseret;


    @Bean
    public com.aliyun.ocr_api20210707.Client createClient() throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accesskeyid)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accesskeyseret);
        // 访问的域名
        config.endpoint = "ocr-api.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.ocr_api20210707.Client(config);
    }
}
