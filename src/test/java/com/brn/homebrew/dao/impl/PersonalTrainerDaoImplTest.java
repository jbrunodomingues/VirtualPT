package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.entity.PersonalTrainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/test-dao.xml"})
public class PersonalTrainerDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PersonalTrainerDao dao;

    @Test
    public void shouldRead() throws Exception {
        //given
        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (1, 'John', 'Doe')");
        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (1)");
        //when
        PersonalTrainer actual = dao.read(1L);
        //then
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCreate() throws Exception {
        //given
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        //when
        dao.create(expected);
        //then
        PersonalTrainer actual = dao.read(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldUpdate() throws Exception {
        //given
        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (1, 'John', 'Doe')");
        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (1)");
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("Louis");
        expected.setLastName("Doe");
        //when
        dao.update(expected);
        //then
        PersonalTrainer actual = dao.read(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (1, 'John', 'Doe')");
        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (1)");
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1L);
        personalTrainer.setFirstName("Louis");
        personalTrainer.setLastName("Doe");
        //when
        dao.delete(personalTrainer);
        //then
        PersonalTrainer actual = dao.read(1L);
        assertNull(actual);
    }

//    @Test
//    public void shouldReadPersonalTrainerWithClient() throws Exception {
//        //given
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (1, 'John', 'Doe')");
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (2, 'Fat', 'Joe')");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (1)");
//        jdbcTemplate.update("INSERT INTO CLIENT (ID_PERSON) VALUES (2)");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER_CLIENT (REFID_PERSONAL_TRAINER, REFID_CLIENT) VALUES (1, 2)");
//        //when
//        PersonalTrainer actual = dao.read(1L);
//        //then
//        PersonalTrainer expected = new PersonalTrainer();
//        expected.setId(1L);
//        expected.setFirstName("John");
//        expected.setLastName("Doe");
//        Client client = new Client();
//        client.setId(2L);
//        client.setFirstName("Fat");
//        client.setLastName("Joe");
//        expected.getClients().add(client);
//        client.getPersonalTrainers().add(expected);
//        assertEquals(expected, actual);
//    }

//    @Test
//    public void shouldReadAllPersonalTrainers() throws Exception {
//        //given
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (1, 'Gonçalo', 'Mosqueira')");
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (2, 'Tatiana', 'Costa')");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (1)");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER (ID_PERSON) VALUES (2)");
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (3, 'Fat', 'Joe')");
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (4, 'Monica', 'Bitch')");
//        jdbcTemplate.update("INSERT INTO PERSON (ID_PERSON, FIRST_NAME, LAST_NAME) VALUES (5, 'Ronald', 'Mcdonalds')");
//        jdbcTemplate.update("INSERT INTO CLIENT (ID_PERSON) VALUES (3)");
//        jdbcTemplate.update("INSERT INTO CLIENT (ID_PERSON) VALUES (4)");
//        jdbcTemplate.update("INSERT INTO CLIENT (ID_PERSON) VALUES (5)");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER_CLIENT (REFID_PERSONAL_TRAINER, REFID_CLIENT) VALUES (1, 3)");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER_CLIENT (REFID_PERSONAL_TRAINER, REFID_CLIENT) VALUES (1, 4)");
//        jdbcTemplate.update("INSERT INTO PERSONAL_TRAINER_CLIENT (REFID_PERSONAL_TRAINER, REFID_CLIENT) VALUES (1, 5)");
//        //when
//        List<PersonalTrainer> actual = dao.readAllFromPersonalTrainer();
//        //then
//        List<PersonalTrainer> expected = new ArrayList<>();
//        expected.add(dao.read(1L));
//        expected.add(dao.read(2L));
//        assertEquals(expected, actual);
//    }
}