package com.brn.homebrew.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

/**
 * @author Bruno Domingues
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_CLIENT");
        List<GrantedAuthority> grantedAuthorityList = Collections.<GrantedAuthority>singletonList(role);
        User user = new User("bruno", "bruno", grantedAuthorityList);
        return user;
    }
}
