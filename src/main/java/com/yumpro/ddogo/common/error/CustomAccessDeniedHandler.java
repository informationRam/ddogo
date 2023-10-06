package com.yumpro.ddogo.common.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 403 에러가 발생할 때 사용자에게 보여줄 페이지로 리디렉션 또는 에러 응답을 설정할 수 있습니다.
        response.sendRedirect("/error/403"); // 403 에러 페이지로 리디렉션
    }
}

