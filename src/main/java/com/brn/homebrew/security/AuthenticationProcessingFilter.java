package com.brn.homebrew.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Bruno Domingues
 */
public class AuthenticationProcessingFilter extends GenericFilterBean {

    //    private AuthenticationManager authenticationManager;
    private Map<String, String> tokenMap = new HashMap<>();

    {
        tokenMap.put("123456", "bruno");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("X-Auth-Token");
        String username = tokenMap.get(token);
        if (username != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl();
            userDetails.setUsername("bruno");
            userDetails.setPassword("bruno");
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_ADMIN");
            userDetails.setRoles(roles);

//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("bruno", "bruno");
//            Authentication authenticate = authenticationManager.authenticate(authRequest);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) request;
    }

//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
}
