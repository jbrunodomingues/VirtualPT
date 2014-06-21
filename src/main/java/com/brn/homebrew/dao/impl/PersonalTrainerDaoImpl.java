package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.entity.PersonalTrainer;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerDaoImpl extends AbstractDao<PersonalTrainer> implements PersonalTrainerDao {

    @Override
    protected Class getTargetClass() {
        return PersonalTrainer.class;
    }
}