package com.yumpro.ddogo.user.security;

import com.yumpro.ddogo.user.security.auth.PrincipalDetail;
import com.yumpro.ddogo.user.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)//@PreAuthorize("isAuthenticated()")//로그인인증가 동작할 수 있기 위함
public class SecurityConfig{

    @Autowired
    private UserSecurityService userSecurityService;

/*
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
*/

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable() // CSRF 보호 비활성화
                /*csrf().and()*/ // CSRF 보호 활성화
                .authorizeHttpRequests();
        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/user/modifyForm/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/user/joinForm")).denyAll() //로그인 후 회원가입접근불가
                .anyRequest().permitAll()
                .and().formLogin().
                loginPage("/user/login").usernameParameter("user_id").passwordParameter("pwd").defaultSuccessUrl("/")
                .and().logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*BCryptPasswordEncoder클래스는 스프링 시큐리티에서 제공되는 클래스이다.이 클래스이용해서  패스워드를 암호화해서 처리하도록 한다.
        bcrypt는 패스워크드를 저장하는 용도로 설계된 해시 함수로
        특정 문자열을 암호화하고,
        체크하는 쪽에서는 암호화된 패스워드가 가능한 패스워드인지만 확인하고
        다시 원문으로 되돌리지는 못한다.*/

        return new BCryptPasswordEncoder();
    }

    //Authentication - 인증=>자신을 증명하는 것
    //AuthenticationManager(인증매니저) - 스프링시큐리티에서 인증을 담당한다
    //AuthenticationManager 생성
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

  /*  protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    }
*/

}