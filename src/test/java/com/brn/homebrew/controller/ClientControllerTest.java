package com.brn.homebrew.controller;

import com.brn.homebrew.model.Client;
import com.brn.homebrew.service.ClientService;
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
    }

    @Test
    public void shouldCreateClient() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        Client client1 = new Client();
        client1.setId(1l);
        client1.setFirstName("John");
        client1.setLastName("Doe");
        ObjectMapper mapper = new ObjectMapper();
        String jsonObj = mapper.writeValueAsString(client);
        when(clientService.create(client)).thenReturn(1l);
        when(clientService.read(1l)).thenReturn(client1);
        //when
        ResultActions resultActions = mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonObj)).andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.firstName").value("John"));
        verify(clientService).create(client);
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
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateTheClient() throws Exception {
        //given
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        ObjectMapper mapper = new ObjectMapper();
        String jsonObj = mapper.writeValueAsString(client);
        //when
        ResultActions resultActions = mockMvc.perform(put("/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonObj)).andDo(print());
        //then
        client.setId(1L);
        resultActions.andExpect(status().isOk());
        verify(clientService).update(client);
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
                .accept(MediaType.APPLICATION_JSON));
        //then
        client.setId(1L);
        resultActions.andExpect(status().isOk());
        verify(clientService).delete(client);
    }
}