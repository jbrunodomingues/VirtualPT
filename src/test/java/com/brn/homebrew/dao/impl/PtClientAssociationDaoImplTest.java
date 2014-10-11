package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/test-dao.xml"})
@DirtiesContext
public class PtClientAssociationDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PtClientAssociationDao ptClientAssociationDao;

    @Test
    public void shouldCreate() throws Exception {
        //given
        insertPtAndClientInDb(1, 2);
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        Client client = new Client();
        client.setId(2l);
        client.setFirstName("Fat");
        client.setLastName("John");
        PtClientAssociation expected = new PtClientAssociation();
        expected.setPersonalTrainer(personalTrainer);
        expected.setClient(client);
        //when
        ptClientAssociationDao.create(expected);
        //then
        PtClientAssociation actual = ptClientAssociationDao.read(1l);
        expected.setId(1l);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        insertPtAndClientInDb(1, 2);
        jdbcTemplate.update("INSERT INTO PT_CLIENT_ASSOCIATION " +
                "(ID_PT_CLIENT_ASSOCIATION, REFID_PERSONAL_TRAINER, REFID_CLIENT) " +
                "VALUES (1, 1 ,2)");
        //when
        PtClientAssociation actual = ptClientAssociationDao.read(1l);
        //then
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        Client client = new Client();
        client.setId(2l);
        client.setFirstName("Fat");
        client.setLastName("John");
        PtClientAssociation expected = new PtClientAssociation();
        expected.setId(1l);
        expected.setPersonalTrainer(personalTrainer);
        expected.setClient(client);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReadAllAssociationsFromPersonalTrainer() throws Exception {
        //given
        prepareDbWith3Pt3Clients3Association();
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        //when
        List<PtClientAssociation> ptClientAssociationList;
        ptClientAssociationList = ptClientAssociationDao.readAllFromPersonalTrainer(personalTrainer);
        //then
        assertEquals(2, ptClientAssociationList.size());
    }

    @Test
    public void shouldReallAllAssociationsFromClient() throws Exception {
        //give
        prepareDbWith3Pt3Clients3Association();
        Client client = new Client();
        client.setId(2l);
        client.setFirstName("Fat");
        client.setLastName("John");
        //when
        List<PtClientAssociation> ptClientAssociationList = ptClientAssociationDao.readAllFromClient(client);
        //then
        assertEquals(1, ptClientAssociationList.size());
    }

    @Test
    public void shouldReadAll() throws Exception {
        //given
        prepareDbWith3Pt3Clients3Association();
        //when
        List<PtClientAssociation> ptClientAssociations = ptClientAssociationDao.readAll();
        //then
        assertEquals(3, ptClientAssociations.size());
    }

    private void prepareDbWith3Pt3Clients3Association() {
        insertPtAndClientInDb(1, 2);
        insertPtAndClientInDb(3, 4);
        insertPtAndClientInDb(5, 6);
        jdbcTemplate.update("INSERT INTO PT_CLIENT_ASSOCIATION " +
                "(ID_PT_CLIENT_ASSOCIATION, REFID_PERSONAL_TRAINER, REFID_CLIENT) " +
                "VALUES (1, 1 ,2)");
        jdbcTemplate.update("INSERT INTO PT_CLIENT_ASSOCIATION " +
                "(ID_PT_CLIENT_ASSOCIATION, REFID_PERSONAL_TRAINER, REFID_CLIENT) " +
                "VALUES (2, 1 ,4)");
        jdbcTemplate.update("INSERT INTO PT_CLIENT_ASSOCIATION " +
                "(ID_PT_CLIENT_ASSOCIATION, REFID_PERSONAL_TRAINER, REFID_CLIENT) " +
                "VALUES (3, 3 ,6)");
    }

    private void insertPtAndClientInDb(int ptId, int clientId) {
        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (? , 'John', 'Doe')", ptId);
        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (?)", ptId);
        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (?, 'Fat', 'John')", clientId);
        jdbcTemplate.update("INSERT INTO CLIENT (ID_PERSON) VALUES (?)", clientId);
    }
}