package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer user_no;             //'회원 번호', PK

    @Column(length = 200)
    private String user_name;            //'회원 이름'

    @Column(unique = true , name ="user_id")
    private String userId;              //'회원 아이디'

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;                 //'생년월일'

    @Column
    private String gender;              //'성별'

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime joinDate;     //'가입일'

    @Column(unique = true)
    private String email;               //'회원 이메일'

    @NotNull
    private String pwd;                 //'비밀번호'


  // 시큐리티 관련

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 목록을 반환
        return authorities;
    }

    @Override
    public String getPassword() {
        // 사용자의 비밀번호를 반환
        return pwd;
    }

    @Override
    public String getUsername() {
        // 사용자의 아이디를 반환
        return user_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 여부를 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨있지 않은지 여부를 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명(비밀번호)이 만료되지 않았는지 여부를 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었는지 여부를 반환
        return true;
    }

}