package com.zero.fastlms.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message = "로그인에 실패하였습니다.";

        if (exception instanceof InternalAuthenticationServiceException) {
            message = exception.getMessage();
        }


        setUseForward(false);  // 포워드 대신 리다이렉트를 사용
        setDefaultFailureUrl("/member/login");

        // 세션에 에러 메시지 설정
        request.getSession().setAttribute("errorMessage", message);

        System.out.println("로그인 실패");
        super.onAuthenticationFailure(request, response, exception);
    }
}