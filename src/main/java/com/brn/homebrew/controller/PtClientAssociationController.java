package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import com.brn.homebrew.service.ClientService;
import com.brn.homebrew.service.MappingService;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.PtClientAssociationService;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("ptClientAssociations")
public class PtClientAssociationController {

    @Autowired
    private PersonalTrainerService personalTrainerService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PtClientAssociationService ptClientAssociationService;
    @Autowired
    private MappingService mappingService;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Transactional
    public PtClientAssociationDto create(@RequestBody PtClientAssociationDto ptClientAssociationDto) {
        PersonalTrainerDto personalTrainerDto = ptClientAssociationDto.getPersonalTrainer();
        ClientDto clientDto = ptClientAssociationDto.getClient();
        if (personalTrainerDto == null
                || personalTrainerDto.getId() == null
                || clientDto == null
                || clientDto.getId() == null) {
            throw new IllegalArgumentException();
        }
        Long id = ptClientAssociationService.create(personalTrainerDto.getId(), clientDto.getId());
        return mappingService.map(ptClientAssociationService.read(id), PtClientAssociationDto.class);
    }

    @Transactional
    @RequestMapping(value = "{id}", method = DELETE)
    @ResponseStatus(OK)
    public void delete(@PathVariable Long id) {
        PtClientAssociation ptClientAssociation = ptClientAssociationService.read(id);
        ptClientAssociationService.delete(ptClientAssociation);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE + ";charset=utf-8")
    @Transactional
    @ResponseStatus(OK)
    public List<PtClientAssociationDto> readAll(@RequestParam(required = false) Long personalTrainerId,
                                                @RequestParam(required = false) Long clientId) {
        List<PtClientAssociation> ptClientAssociationList;
        if (personalTrainerId != null) {
            PersonalTrainer personalTrainer = personalTrainerService.read(personalTrainerId);
            ptClientAssociationList = ptClientAssociationService.readAllFromPersonalTrainer(personalTrainer);
        } else if (clientId != null) {
            Client client = clientService.read(clientId);
            ptClientAssociationList = ptClientAssociationService.readAllFromClient(client);
        } else {
            ptClientAssociationList = Collections.emptyList();
        }
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