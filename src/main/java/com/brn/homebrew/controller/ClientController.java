package com.brn.homebrew.controller;

import com.brn.homebrew.entity.Client;
import com.brn.homebrew.entity.PointGPS;
import com.brn.homebrew.entity.Track;
import com.brn.homebrew.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void create(@RequestBody Client client) {
        clientService.create(client);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Client read(@PathVariable Long id) {
        return clientService.read(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        clientService.update(client);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Client client = clientService.read(id);
        clientService.delete(client);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
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
