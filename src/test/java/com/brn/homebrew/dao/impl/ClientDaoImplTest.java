package com.brn.homebrew.dao.impl;

import com.brn.homebrew.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/test-dao.xml"})
@DirtiesContext
public class ClientDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ClientDaoImpl dao;
    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void init() {
        resetPrimaryKeyAutoIncrementedValue();
    }

    @Test
    public void shouldCreate() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        //when
        dao.create(client);
        persist();
        //then
        Client dbClient = readClientFromDb();
        assertEquals("Inserted client and client in db don't match", client, dbClient);
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Batatas");
        dao.create(client);
        persist();
        //when
        Client dbClient = dao.read(1L);
        //then
        assertEquals(client, dbClient);
    }

    @Test
    public void shouldUpdate() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        dao.create(client);
        //when
        client.setFirstName("Louise");
        persist();
        //then
        Client dbClient = readClientFromDb();
        assertEquals("Inserted client and client in db don't match", client, dbClient);
        assertEquals(client, dbClient);
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        dao.create(client);
        persist();
        client = dao.read(1L);
        //when
        dao.delete(client);
        persist();
        //then
        assertEquals("There can't be any entry in table", 0, countRowsInTable("PERSON"));
    }

    /**
     * Even though transactions are roled back per method the id is incremented in the
     * database and causes dependencies between tests. By reseting the problem is solved.
     */
    private void resetPrimaryKeyAutoIncrementedValue() {
        jdbcTemplate.update("ALTER TABLE PERSON ALTER COLUMN ID_PERSON RESTART WITH 1");
    }

    /**
     * Force flush for testing purposes
     */
    private void persist() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.flush();
    }

    private Client readClientFromDb() {
        return jdbcTemplate.queryForObject(
                "SELECT ID_PERSON, FIRST_NAME, LAST_NAME " +
                        "FROM CLIENT " +
                        "INNER JOIN PERSON " +
                        "ON ID_PERSON = ID_PERSON",
                new ClientMapper()
        );
    }

    private class ClientMapper implements RowMapper<Client> {

        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId(rs.getLong(1));
            client.setFirstName(rs.getString(2));
            client.setLastName(rs.getString(3));
            return client;
        }
    }
}
