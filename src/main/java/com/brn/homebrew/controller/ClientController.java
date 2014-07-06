package com.brn.homebrew.controller;

import com.brn.homebrew.entity.Client;
import com.brn.homebrew.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @Transactional
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void create(@RequestBody Client client) {
        clientService.create(client);
    }

    @Transactional
    @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Client read(@PathVariable Long id) {
        Client read = clientService.read(id);
        Client client = new Client();
        client.setId(read.getId());
        client.setFirstName(read.getFirstName());
        client.setLastName(read.getLastName());
        return client;
    }

    @Transactional
    @RequestMapping(value = "{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        clientService.update(client);
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
    public List<Client> readAll() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        clientList.add(new Client());
        return clientList;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleException1(HttpMessageNotReadableException ex) {
        return ex.getMessage();
    }
}
