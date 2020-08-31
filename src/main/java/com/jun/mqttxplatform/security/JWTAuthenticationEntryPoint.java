package com.jun.mqttxplatform.security;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jun.mqttxplatform.entity.Response;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Response<Void> result;
        if (e instanceof InsufficientAuthenticationException) {
            result = Response.fail("用户未认证");
        }else if (e instanceof BadCredentialsException){
            Throwable cause = e.getCause();
            if (cause instanceof TokenExpiredException) {
                result = Response.fail("令牌过期");
            }else {
                result = Response.fail("令牌错误");
            }
        }else {
            result = Response.fail("令牌错误");
        }


        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
    }
}
