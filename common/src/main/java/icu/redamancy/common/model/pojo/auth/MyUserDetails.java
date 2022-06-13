package icu.redamancy.common.model.pojo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class MyUserDetails implements UserDetails {
    private EntityUser eUser;
    private String username;

    @JsonIgnore
    private String password;
    private List<GrantedAuthority> authorities;


    public MyUserDetails(EntityUser eUser, String username, String password, List<GrantedAuthority> authorities) {
        this.eUser = eUser;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //    重写了equals按道理应该要重写hashcode 但该方法中使用了但String但equals方法，已经重写过了，所以说无需重写
    @Override
    public boolean equals(Object obj) {
        //会话并发生效，使用username判断是否是同一个用户
        if (obj instanceof MyUserDetails) {
            //字符串的equals方法是已经重写过的
            return ((MyUserDetails) obj).getUsername().equals(this.username);
        } else {
            return false;
        }
    }
}
