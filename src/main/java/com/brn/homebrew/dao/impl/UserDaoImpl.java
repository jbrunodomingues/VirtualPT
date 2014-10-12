package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.UserDao;
import com.brn.homebrew.model.User;
import org.hibernate.Criteria;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author Bruno Domingues
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    public User findUserByUserName(String username) {
        Criteria criteria = getSession().createCriteria(getTargetClass());
        criteria.add(eq("username", username));
        return (User) criteria.uniqueResult();
    }

    @Override
    protected Class getTargetClass() {
        return User.class;
    }
}
