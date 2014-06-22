package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.PersonalTrainerDao;
import com.brn.homebrew.entity.PersonalTrainer;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerDaoImpl extends AbstractDao<PersonalTrainer> implements PersonalTrainerDao {

    @Override
    protected Class getTargetClass() {
        return PersonalTrainer.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonalTrainer> readAll() {
        return getSession().createCriteria(PersonalTrainer.class).list();
    }
}