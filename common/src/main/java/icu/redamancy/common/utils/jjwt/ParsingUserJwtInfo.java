package icu.redamancy.common.utils.jjwt;

import icu.redamancy.common.utils.handlerequest.HandlerParameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.nio.charset.StandardCharsets;

/**
 * @Author redamancy
 * @Date 2022/5/18 16:52
 * @Version 1.0
 */
public class ParsingUserJwtInfo {


    private static final String USERID = "userId";

    private static class GetParsingUserJwtInfo {
        private static final ParsingUserJwtInfo INSTANCE = new ParsingUserJwtInfo();
    }

    public static ParsingUserJwtInfo GetParsingUserJwtInfo() {
        return ParsingUserJwtInfo.GetParsingUserJwtInfo.INSTANCE;
    }

    public String getUserId() {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getDetails();
        String jwt = details.getTokenValue();

        Claims claims = Jwts.parser()
                .setSigningKey("dev".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get(USERID).toString();
    }

}
