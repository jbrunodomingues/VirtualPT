package com.brn.homebrew.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bruno Domingues
 */
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream out = response.getOutputStream();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.addCookie(new Cookie("JSESSIONID", ""));
        AuthenticationFailureDto authenticationFailureDto = new AuthenticationFailureDto();
        authenticationFailureDto.setCode(HttpStatus.UNAUTHORIZED.value());
        authenticationFailureDto.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        String authenticationFailureJson = new ObjectMapper().writeValueAsString(authenticationFailureDto);
        out.write(authenticationFailureJson.getBytes());
        out.flush();
    }
}
