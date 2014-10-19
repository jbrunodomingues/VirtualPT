package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.UserDao;
import com.brn.homebrew.model.Role;
import com.brn.homebrew.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Domingues
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUserName(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorityList);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}