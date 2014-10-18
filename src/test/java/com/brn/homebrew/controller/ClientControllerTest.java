package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.service.ClientService;
import com.brn.homebrew.service.impl.MappingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Bruno Domingues
 */
public class ClientControllerTest {

    MockMvc mockMvc;
    @Mock
    ClientService clientService;
    @InjectMocks
    ClientController clientController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(clientController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
        ControllerTestsHelper.setDependencyUsingReflection(clientController, "mappingService", new MappingServiceImpl());
    }

    @Test
    public void shouldCreateClient() throws Exception {
        //given
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        Client clientToBeCreated = new Client();
        clientToBeCreated.setFirstName("John");
        clientToBeCreated.setLastName("Doe");
        Client clientToBeReturned = new Client();
        clientToBeReturned.setId(1L);
        clientToBeReturned.setFirstName("John");
        clientToBeReturned.setLastName("Doe");
        String clientJson = new ObjectMapper().writeValueAsString(clientDto);
        when(clientService.create(clientToBeCreated)).thenReturn(1l);
        when(clientService.read(1l)).thenReturn(clientToBeReturned);
        //when
        ResultActions resultActions = mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
        verify(clientService).create(clientToBeCreated);
    }

    @Test
    public void shouldReadClient() throws Exception {
        //given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        when(clientService.read(1L)).thenReturn(client);
        //when
        ResultActions resultActions = mockMvc.perform(get("/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void shouldUpdateTheClient() throws Exception {
        //given
        Client existingClient = new Client();
        existingClient.setId(1l);
        existingClient.setFirstName("John");
        existingClient.setLastName("Doe");
        Client clientToUpdate = new Client();
        clientToUpdate.setId(1l);
        clientToUpdate.setFirstName("John");
        clientToUpdate.setLastName("Tosvalds");
        ClientDto clientDto = new ClientDto();
        clientDto.setLastName("Tosvalds");
        String jsonObj = new ObjectMapper().writeValueAsString(clientDto);
        when(clientService.read(1l)).thenReturn(existingClient);
        when(clientService.read(1l)).thenReturn(clientToUpdate);
        //when
        ResultActions resultActions = mockMvc.perform(put("/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonObj))
                .andDo(print());
        //then
        existingClient.setId(1L);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Tosvalds"));
        verify(clientService).update(clientToUpdate);
    }

    @Test
    public void shouldDeleteTheClient() throws Exception {
        //given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        when(clientService.read(1L)).thenReturn(client);
        //when
        ResultActions resultActions = mockMvc.perform(delete("/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        verify(clientService).delete(client);
    }
}