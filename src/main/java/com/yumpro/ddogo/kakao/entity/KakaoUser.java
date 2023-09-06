package com.yumpro.ddogo.kakao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // UserDetails 임포트 추가

import java.util.Collection;

@Data
@Entity
public class KakaoUser implements UserDetails { // UserDetails를 구현합니다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String accessToken;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String kakaoUserId;

    @Column
    private String nickname;

    // UserDetails 인터페이스의 메서드를 구현합니다.

    // 사용자의 식별자를 반환합니다. 여기에서는 카카오 사용자 ID를 반환합니다.
    @Override
    public String getUsername() {
        return this.kakaoUserId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 암호화된 비밀번호를 반환합니다. 여기에서는 카카오 사용자의 비밀번호가 아닌 다른 필드를 반환하거나 null을 반환할 수 있습니다.
    @Override
    public String getPassword() {
        return null; // 비밀번호를 사용하지 않는 경우 null을 반환하거나, 암호화된 비밀번호를 반환합니다.
    }

    // 아래의 메서드들은 UserDetails 인터페이스에서 요구하는 메서드들입니다.
    // 사용자 계정의 잠김 여부, 권한 등을 관리할 때 사용됩니다.

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부를 반환 (기본적으로 true)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부를 반환 (기본적으로 true)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호) 만료 여부를 반환 (기본적으로 true)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부를 반환 (기본적으로 true)
    }
}
