package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.UserDao;
import com.brn.homebrew.model.Role;
import com.brn.homebrew.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bruno Domingues
 */
public class UserDetailsServiceImplTest {

    private UserDao userDaoMock;
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void setUp() throws Exception {
        userDaoMock = mock(UserDao.class);
        userDetailsService = new UserDetailsServiceImpl();
        userDetailsService.setUserDao(userDaoMock);
    }

    @Test
    public void shouldReturnUser() throws Exception {
        //given
        User user = new User();
        user.setId(1l);
        user.setUsername("username");
        user.setPassword("password");
        Role role = new Role();
        role.setId(1l);
        role.setRoleName("ROLE_ADMIN");
        user.setRoles(Collections.singleton(role));
        when(userDaoMock.findUserByUserName("username")).thenReturn(user);
        //when
        UserDetails actualUserDetails = userDetailsService.loadUserByUsername("username");
        //then
        org.springframework.security.core.userdetails.User expectedUserDetails =
                new org.springframework.security.core.userdetails.User(
                        "username",
                        "password",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test
    public void shouldReturnNullForUserNotFound() throws Exception {
        //given
        when(userDaoMock.findUserByUserName("nonExistingUsername")).thenReturn(null);
        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername("nonExistingUsername");
        //then
        assertNull(userDetails);
    }
}