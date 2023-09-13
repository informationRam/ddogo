package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.yumpro.ddogo.user.reprository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        System.out.println("시큐리티 로그인 진행 : UserSecurityService 진입");
        Optional<com.yumpro.ddogo.common.entity.User> user = userRepository.findByUserId(user_id);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + user_id);
        }

        com.yumpro.ddogo.common.entity.User user1 = user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(user1.getPwd());
        if ("admin".equals(user_id)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));

            // 세션에 user1 정보 저장
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user1, null, authorities));
            System.out.println("getUserId:" + user1.getUserId());
            System.out.println("getUse:" + user1);

            return new User(user1.getUserId(), user1.getPwd(), new ArrayList<>());
        }
        return new User(user1.getUserId(), user1.getPwd(), authorities);
    }
}
