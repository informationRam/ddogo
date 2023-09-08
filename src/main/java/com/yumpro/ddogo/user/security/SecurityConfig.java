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
public class SecurityConfig {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable() // CSRF 보호 비활성화
                /*csrf().and()*/ // CSRF 보호 활성화
                .authorizeHttpRequests();
        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/user/modifyForm/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/manager/**")).hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin().
                loginPage("/user/login").usernameParameter("user_id").passwordParameter("pwd").defaultSuccessUrl("/")
                .and().logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true)
               /* .and()
                .oauth2Login() // 외부 인증
                .loginPage("/user/kakaoform") // oauth2Login 시 loginForm
                .userInfoEndpoint()
                .userService(customOAuth2UserService)*/; // SNS 로그인이 완료된 뒤 후처리가 필요함. 엑세스토큰+사용자프로필 정보

                /*.and().exceptionHandling().accessDeniedPage("/common/ddoError")
                .and()
                .exceptionHandling() // 예외 처리 설정을 추가합니다.
                .accessDeniedPage("/common/ddoError.html"); // 403 에러 발생 시 커스텀 에러 페이지로 리디렉션합니다.*/

        return http.build();
    }
    /* @Bean  ->기존내용
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                         .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                 .csrf((csrf) -> csrf
                         .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                 .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                         XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                 .formLogin((formLogin) -> formLogin.loginPage("/user/login").usernameParameter("user_id")
                         .passwordParameter("pwd")
                         .defaultSuccessUrl("/"))
                 .logout((logout) -> logout
                         .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                         .logoutSuccessUrl("/")
                         .invalidateHttpSession(true))   //세션날리기
                 .exceptionHandling().accessDeniedPage("/common/ddoError");
         ;
         return http.build();
     }*/
    /*    @Bean  -> 403 에러 방지
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                    .csrf().disable()
                    .formLogin((formLogin) -> formLogin.loginPage("/user/login").usernameParameter("user_id")
                            .passwordParameter("pwd")
                            .defaultSuccessUrl("/"))
                    .logout((logout) -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true))   //세션날리기
                    .exceptionHandling().accessDeniedPage("/common/ddoError");
            ;
            return http.build();
        }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        /*BCryptPasswordEncoder클래스는 스프링 시큐리티에서 제공되는 클래스이다.이 클래스이용해서  패스워드를 암호화해서 처리하도록 한다.
        bcrypt는 패스워크드를 저장하는 용도로 설계된 해시 함수로
        특정 문자열을 암호화하고,
        체크하는 쪽에서는 암호화된 패스워드가 가능한 패스워드인지만 확인하고
        다시 원문으로 되돌리지는 못한다.(교재 p651참고)*/

        return new BCryptPasswordEncoder();
    }

    //참고(교재 615)
    //Authentication - 인증=>자신을 증명하는 것
    //AuthenticationManager(인증매니저) - 스프링시큐리티에서 인증을 담당한다
    //AuthenticationManager 생성
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}



