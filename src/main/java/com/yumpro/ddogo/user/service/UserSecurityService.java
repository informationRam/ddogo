package com.yumpro.ddogo.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.validation.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    // Load user details using user_id

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 진입");
        System.out.println("user_id:"+user_id);
   /*     Optional<com.yumpro.ddogo.user.entity.User> user = userRepository.findByUserId(user_id);*/
        Optional<com.yumpro.ddogo.user.entity.User> user = userRepository.findByUserId(user_id);

        System.out.println("user:" + user);
        if (user.isEmpty()) {
            System.out.println("if");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        com.yumpro.ddogo.user.entity.User user1 = user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(user1.getPwd());
        if ("admin".equals(user_id)) {
            System.out.println("요기까지11??");
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            System.out.println("요기까지?");
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(user1.getUserId(), user1.getPwd(), authorities);
    }


}
