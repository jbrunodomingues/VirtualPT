package com.brn.homebrew.service;

import com.brn.homebrew.entity.PersonalTrainer;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public interface PersonalTrainerService extends Service<PersonalTrainer> {

    List<PersonalTrainer> readAll();
}
