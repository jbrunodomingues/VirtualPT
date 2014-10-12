package com.brn.homebrew.security;

import com.brn.homebrew.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/rest-servlet.xml", "classpath:test-dao.xml", "classpath:test.xml"})
@WebAppConfiguration
@DirtiesContext
public class AuthenticationTest {

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
    public void shouldAccessResource() throws Exception {
        //given
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_ADMIN");
        List<GrantedAuthority> grantedAuthorityList = Collections.<GrantedAuthority>singletonList(role);
        User user = new User("username", "password", grantedAuthorityList);
        when(tokenService.getUser("validToken")).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(user);
        //when
        ResultActions resultActions = mockMvc.perform(get("/testingAccessResource")
                .contentType(APPLICATION_JSON)
                .header("X-Auth-Token", "validToken")
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void shouldBeDeniedResourceAccess() throws Exception {
        //given
        when(tokenService.getUser("validToken")).thenReturn("username");
        //when
        ResultActions resultActions = mockMvc.perform(get("/testingAccessResource")
                .contentType(APPLICATION_JSON)
                .header("X-Auth-Token", "invalidToken")
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isForbidden());
    }
}