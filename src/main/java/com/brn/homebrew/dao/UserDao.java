package com.brn.homebrew.dao;

import com.brn.homebrew.model.User;

/**
 * @author Bruno Domingues
 */
public interface UserDao extends Dao<User> {

    User findUserByUserName(String name);
}
