package icu.redamancy.redamancyauthenticationcenter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
@ConfigurationProperties(prefix = "wx")
public  class WxApiProperties {

    private String appid;
    private String appsercret;
}
