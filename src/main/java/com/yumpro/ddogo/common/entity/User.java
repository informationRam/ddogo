package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 시큐리티 추가

        @Column(unique = true)
        private String kakaoId; // Add a property for kakaoId

        public String getKakaoId() {
            return kakaoId;
        }

        public void setKakaoId(String kakaoId) {
            this.kakaoId = kakaoId;
        }

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
        @Column(name = "role_name")
        private Collection<String> authorities;

        @Override
        public String getPassword() {
            return pwd; // 사용자의 비밀번호 필드 반환
        }

        @Override
        public String getUsername() {
            return userId; // 사용자의 아이디 필드 반환
        }

        @Override
        public boolean isAccountNonExpired() {
            return true; // 계정이 만료되지 않았음을 반환
        }

        @Override
        public boolean isAccountNonLocked() {
            return true; // 계정이 잠겨있지 않음을 반환
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true; // 자격 증명(비밀번호)이 만료되지 않았음을 반환
        }

        @Override
        public boolean isEnabled() {
            return true; // 계정이 활성화되었음을 반환
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = new ArrayList<>();

            for (String roleName : this.authorities) {
                authorities.add(new SimpleGrantedAuthority(roleName));
            }

            return authorities;
        }
    }
