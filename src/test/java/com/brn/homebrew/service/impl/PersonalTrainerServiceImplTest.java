package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerServiceImplTest {

    @Test
    public void shouldCreate() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1L);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        PersonalTrainerService personalTrainerService = new PersonalTrainerServiceImpl(personalTrainerDaoMock);
        //when
        personalTrainerService.create(personalTrainer);
        //then
        verify(personalTrainerDaoMock).create(personalTrainer);
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        when(personalTrainerDaoMock.read(1L)).thenReturn(expected);
        PersonalTrainerService personalTrainerService = new PersonalTrainerServiceImpl(personalTrainerDaoMock);
        //when
        PersonalTrainer actual = personalTrainerService.read(1L);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldUpdate() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1L);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        PersonalTrainerService personalTrainerService = new PersonalTrainerServiceImpl(personalTrainerDaoMock);
        //when
        personalTrainerService.update(personalTrainer);
        //then
        verify(personalTrainerDaoMock).update(personalTrainer);
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1L);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        PersonalTrainerDao personalTrainerDaoMock = mock(PersonalTrainerDao.class);
        PersonalTrainerService personalTrainerService = new PersonalTrainerServiceImpl(personalTrainerDaoMock);
        //when
        personalTrainerService.delete(personalTrainer);
        //then
        verify(personalTrainerDaoMock).delete(personalTrainer);
    }

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