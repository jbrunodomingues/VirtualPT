package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.entity.PtClientAssociation;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.PtClientAssociationService;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("ptClientAssociations")
public class PtClientAssociationController {

    @Autowired
    private PersonalTrainerService personalTrainerService;
    @Autowired
    private PtClientAssociationService ptClientAssociationService;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Transactional
    public void create(@RequestBody PtClientAssociationDto ptClientAssociationDto) {
//        PersonalTrainer personalTrainer = personalTrainerService.read(ptClientAssociationDto.getPersonalTrainer().getId());
//        Client client = clientService.read(ptClientAssociationDto.getClient().getId());
//        personalTrainer.getClients().add(client);
//        personalTrainerService.update(personalTrainer);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE + ";charset=utf-8")
    @Transactional
    @ResponseStatus(OK)
    public List<PtClientAssociationDto> readAllFromPersonalTrainer(@RequestParam Long personalTrainerId) {
        PersonalTrainer personalTrainer = personalTrainerService.read(personalTrainerId);
        List<PtClientAssociation> ptClientAssociationList = ptClientAssociationService.readAllFromPersonalTrainer(personalTrainer);
        List<PtClientAssociationDto> dtoList = new ArrayList<>();
        for (PtClientAssociation ptClientAssociation : ptClientAssociationList) {
            dtoList.add(getMapper().map(ptClientAssociation, PtClientAssociationDto.class));
        }
        return dtoList;
    }

    private Mapper getMapper() {
        return DozerBeanMapperSingletonWrapper.getInstance();
    }
}
