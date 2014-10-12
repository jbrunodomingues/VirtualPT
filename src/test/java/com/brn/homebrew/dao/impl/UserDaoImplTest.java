package com.brn.homebrew.dao.impl;

import com.brn.homebrew.model.Role;
import com.brn.homebrew.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/test-dao.xml"})
@DirtiesContext
public class UserDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserDaoImpl userDao;

    @Test
    public void shouldFindUserWithGivenName() throws Exception {
        //given
        create2UsersAnd2Roles();
        //when
        User actualUser = userDao.findUserByUserName("username2");
        //then
        User expectedUser = new User();
        expectedUser.setId(2L);
        expectedUser.setUsername("username2");
        expectedUser.setPassword("password2");
        Role role = new Role();
        role.setId(2L);
        role.setRoleName("ROLE_ADMIN");
        expectedUser.setRoles(Collections.singleton(role));
        assertEquals(expectedUser, actualUser);
    }

    private void create2UsersAnd2Roles() {
        jdbcTemplate.update("INSERT INTO USER (ID_USER, USERNAME, PASSWORD) VALUES (1, 'username', 'password')");
        jdbcTemplate.update("INSERT INTO USER (ID_USER, USERNAME, PASSWORD) VALUES (2, 'username2', 'password2')");
        jdbcTemplate.update("INSERT INTO ROLE (ID_ROLE, ROLE_NAME, REFID_USER) VALUES (1, 'ROLE_ADMIN', 1)");
        jdbcTemplate.update("INSERT INTO ROLE (ID_ROLE, ROLE_NAME, REFID_USER) VALUES (2, 'ROLE_ADMIN', 2)");
    }

    @Test
    public void shouldNotFindUserWithGivenName() throws Exception {
        //given
        create2UsersAnd2Roles();
        //when
        User user = userDao.findUserByUserName("nonExistingUserName");
        //then
        assertNull(user);
    }
}
