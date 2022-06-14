package icu.redamancy.redamancyauthenticationcenter.service.imlp.Auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.redamancy.common.model.dao.auth.UserDaoServiceImpl;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.model.dao.auth.mapper.UserMapper;
import icu.redamancy.common.model.pojo.auth.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Service
public class KiteUserDetailsService implements UserDetailsService {

    private static final String LOGIN_TYPE = "loginType";

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserDaoServiceImpl userDaoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String login_type = request.getHeader(LOGIN_TYPE);


        EntityUser entityUser = null;

        if (login_type.equals("wx")){
            entityUser = userMapper.selectOne(new QueryWrapper<EntityUser>()
                    .eq("openid", username));

            return new MyUserDetails(entityUser, entityUser.getOpenid(), passwordEncoder.encode(entityUser.getOpenid()), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }else {
            entityUser = userDaoService.lambdaQuery().eq(EntityUser::getUsername,username).one();

            return new MyUserDetails(entityUser, entityUser.getUsername(), passwordEncoder.encode(entityUser.getPassword()),AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        }


//        为用户添加权限
//        应在数据库中查询
//        String role = "ROLE_ADMIN";
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role));

    }
}
