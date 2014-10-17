package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.service.ClientService;
import com.brn.homebrew.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private MappingService mappingService;

    @Transactional
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ClientDto create(@RequestBody Client client) {
        Long clientId = clientService.create(client);
        return mappingService.map(clientService.read(clientId), ClientDto.class);
    }

    @Transactional
    @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ClientDto read(@PathVariable Long id) {
        Client client = clientService.read(id);
        return mappingService.map(client, ClientDto.class);
    }

    @Transactional
    @RequestMapping(value = "{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ClientDto update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        Client client = clientService.read(id);
        mappingService.map(clientDto, client);
        clientService.update(client);
        return mappingService.map(clientService.read(id), ClientDto.class);
    }

    @Transactional
    @RequestMapping(value = "{id}", method = DELETE)
    @ResponseStatus(OK)
    public void delete(@PathVariable Long id) {
        Client client = clientService.read(id);
        clientService.delete(client);
    }

    @Transactional
    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    public List<ClientDto> readAll() {
        return new ArrayList<>();
    }
}