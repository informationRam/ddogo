package com.yumpro.ddogo.user.security.auth;

import com.yumpro.ddogo.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 userdetails타입의 오브젝트를
// 스프링 시큐리의 고유한 세션저장소에 저장해준다.
public class PrincipalDetail implements UserDetails {

    private User user;


    public PrincipalDetail(User user) {
        this.user = user;

    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getUser_name();
    }

    //계정이 만료되지 않았는지 리턴한다,(t : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    //계정이 잠기지 않았는지 리턴한다,(t : 잠기지않음)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 비밀번호가 만료되지 않았는지 리턴(t: 활성화)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정이 활성화(사용가능)인지 리턴(t: 활성화)
    @Override
    public boolean isEnabled() {
        return false;
    }

    // 계정의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();

        return collectors;
    }
}
