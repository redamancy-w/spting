package icu.redamancy.common.config.openfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import icu.redamancy.common.utils.handlerequest.HandlerParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = HandlerParameter.getHandlerParameter().getRequest();
        System.out.println("request" + request.getHeader("ad"));
        Enumeration<String> er = request.getHeaderNames();

        assert er != null : "请求为空";

        while (er.hasMoreElements()) {
            String key = (String) er.nextElement();
            String value = request.getHeader(key);
            if (!"content-length".equals(key)) {
                log.info(key + "=" + value);
                try {
                    requestTemplate.header(key, value);
                } catch (Exception e) {
                    log.error("请求头设置失败");
                }
            }
        }
    }
}
