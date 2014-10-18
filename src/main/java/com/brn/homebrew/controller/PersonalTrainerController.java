package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.service.MappingService;
import com.brn.homebrew.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("personalTrainers")
public class PersonalTrainerController {

    @Autowired
    private PersonalTrainerService personalTrainerService;
    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON_VALUE + ";charset=utf-8")
    @Transactional
    @ResponseStatus(OK)
    public PersonalTrainerDto read(@PathVariable Long id) {
        PersonalTrainer personalTrainer = personalTrainerService.read(id);
        return mappingService.map(personalTrainer, PersonalTrainerDto.class);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE + ";charset=utf-8")
    @Transactional
    @ResponseStatus(OK)
    public List<PersonalTrainerDto> readAll() {
        List<PersonalTrainer> personalTrainerList = personalTrainerService.readAll();
        List<PersonalTrainerDto> dtoList = new ArrayList<>();
        for (PersonalTrainer personalTrainer : personalTrainerList) {
            dtoList.add(mappingService.map(personalTrainer, PersonalTrainerDto.class));
        }
        return dtoList;
    }
}