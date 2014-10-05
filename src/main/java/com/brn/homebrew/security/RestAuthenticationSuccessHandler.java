package com.brn.homebrew.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bruno Domingues
 */
public class RestAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//        if (savedRequest == null) {
//            clearAuthenticationAttributes(request);
//            return;
//        }
//        String targetUrlParam = getTargetUrlParameter();
//        if (isAlwaysUseDefaultTargetUrl() ||
//                (targetUrlParam != null &&
//                        StringUtils.hasText(request.getParameter(targetUrlParam)))) {
//            requestCache.removeRequest(request, response);
//            clearAuthenticationAttributes(request);
//            return;
//        }
        String sessionId = request.getSession().getId();
        AuthenticationSuccessDto authenticationSuccessDto = new AuthenticationSuccessDto();
        authenticationSuccessDto.setCode(HttpStatus.OK.value());
        authenticationSuccessDto.setSessionId(sessionId);
        String authenticationSucessJson = new ObjectMapper().writeValueAsString(authenticationSuccessDto);
//        response.addCookie(new Cookie("JSESSIONID", ""));
        response.getOutputStream().write(authenticationSucessJson.getBytes());
        response.getOutputStream().flush();
        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
