package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.model.PersonalTrainer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerServiceImplTest {

    @Test
    public void shouldReadAll() throws Exception {
        //given
        List<PersonalTrainer> expected = new ArrayList<>();
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1L);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Dow");
        expected.add(personalTrainer);
        personalTrainer = new PersonalTrainer();
        personalTrainer.setId(2L);
        personalTrainer.setFirstName("Janice");
        personalTrainer.setLastName("Joplin");
        expected.add(personalTrainer);
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        when(personalTrainerDaoMock.readAll()).thenReturn(expected);
        PersonalTrainerServiceImpl personalTrainerService = new PersonalTrainerServiceImpl();
        personalTrainerService.setPersonalTrainerDao(personalTrainerDaoMock);
        //when
        List<PersonalTrainer> actual = personalTrainerService.readAll();
        //then
        assertEquals(expected, actual);
    }
}