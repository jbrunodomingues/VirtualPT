package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.AuthenticationDto;
import com.brn.homebrew.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/rest-servlet.xml",
        "classpath:test-dao.xml",
        "classpath:test.xml"})
@WebAppConfiguration
@DirtiesContext
public class AuthenticationControllerTest {

    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void shouldFailAuthenticationWithWrongCredentials() throws Exception {
        //given
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername("username");
        authenticationDto.setPassword("wrongPassword");
        User user = new User("username", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(userDetailsService.loadUserByUsername("username")).thenReturn(user);
        String authenticationJson = new ObjectMapper().writeValueAsString(authenticationDto);
        //when
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(authenticationJson))
                .andDo(print());
        //then
        resultActions.andExpect(status().isUnauthorized());
        resultActions.andExpect(jsonPath("$.code").value(UNAUTHORIZED.value()));
        resultActions.andExpect(jsonPath("$.message").value("bad credentials"));
    }

    @Test
    public void shouldAuthenticateAndReceiveToken() throws Exception {
        //given
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername("username");
        authenticationDto.setPassword("password");
        User user = new User("username", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(userDetailsService.loadUserByUsername("username")).thenReturn(user);
        String authenticationJson = new ObjectMapper().writeValueAsString(authenticationDto);
        when(tokenService.createTokenForUser("username")).thenReturn("123456");
        //when
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(authenticationJson))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.token").value("123456"));
    }
}