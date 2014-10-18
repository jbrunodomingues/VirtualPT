package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerServiceImpl extends AbstractService<PersonalTrainer> implements PersonalTrainerService {

    @Override
    public List<PersonalTrainer> readAll() {
        return ((PersonalTrainerDao) dao).readAll();
    }

    public void setPersonalTrainerDao(PersonalTrainerDao personalTrainerDao) {
        setDao(personalTrainerDao);
    }
}