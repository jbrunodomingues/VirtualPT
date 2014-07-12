package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;
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
        PersonalTrainerDao daoMock = mock(PersonalTrainerDao.class);
        when(daoMock.readAll()).thenReturn(expected);
        PersonalTrainerService personalTrainerService = new PersonalTrainerServiceImpl(daoMock);
        //when
        List<PersonalTrainer> actual = personalTrainerService.readAll();
        //then
        assertEquals(expected, actual);
    }
}