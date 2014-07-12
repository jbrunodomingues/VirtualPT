package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.brn.homebrew.entity.Client;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.entity.PtClientAssociation;
import com.brn.homebrew.service.ClientService;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.PtClientAssociationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PersonalTrainerService personalTrainerService;
    @Mock
    private PtClientAssociationService ptClientAssociationService;
    @Mock
    private ClientService clientService;
    @InjectMocks
    private PtClientAssociationController ptClientAssociationController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(ptClientAssociationController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void shouldCreateAssociation() throws Exception {
        //given
        PersonalTrainer personalTrainer = createPersonalTrainer();
        Client client = createClient(2l, "Fat", "Joe");
        PtClientAssociation ptClientAssociation = new PtClientAssociation();
        ptClientAssociation.setPersonalTrainer(personalTrainer);
        ptClientAssociation.setClient(client);
        PtClientAssociationDto associationDto = createPtClientAssociationDto();
        ObjectMapper mapper = new ObjectMapper();
        String jsonObj = mapper.writeValueAsString(associationDto);
        when(personalTrainerService.read(1l)).thenReturn(personalTrainer);
        when(clientService.read(2l)).thenReturn(client);
        ResultActions resultActions = mockMvc.perform(post("/ptClientAssociations")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(jsonObj))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        verify(ptClientAssociationService).create(ptClientAssociation);
    }

    private PtClientAssociationDto createPtClientAssociationDto() {
        ClientDto client = new ClientDto();
        client.setId(2l);
        client.setFirstName("John");
        client.setLastName("Doe");
        PersonalTrainerDto personalTrainer = new PersonalTrainerDto();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("Goncalo");
        personalTrainer.setLastName("Mosqueira");
        PtClientAssociationDto association = new PtClientAssociationDto();
        association.setClient(client);
        association.setPersonalTrainer(personalTrainer);
        return association;
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        PersonalTrainer personalTrainer = createPersonalTrainer();
        Client client = createClient(2l, "Fat", "Joe");
        PtClientAssociation ptClientAssociation = new PtClientAssociation();
        ptClientAssociation.setId(1l);
        ptClientAssociation.setPersonalTrainer(personalTrainer);
        ptClientAssociation.setClient(client);
        when(ptClientAssociationService.read(1l)).thenReturn(ptClientAssociation);
        //when
        ResultActions resultActions = mockMvc.perform(delete("/ptClientAssociations/{id}", 1)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        verify(ptClientAssociationService).delete(ptClientAssociation);
    }

    @Test
    public void shouldGetAllAssociationsForPersonalTrainer() throws Exception {
        //given
        PersonalTrainerDto ptDto = new PersonalTrainerDto();
        ptDto.setId(1l);
        ptDto.setFirstName("John");
        ptDto.setLastName("Doe");
        PersonalTrainer pt = createPersonalTrainer();
        Client client1 = createClient(2l, "Fat", "Joe");
        Client client2 = createClient(3l, "Chubby", "Mike");
        List<PtClientAssociation> associationList = new ArrayList<>();
        addAssociationToList(1l, pt, client1, associationList);
        addAssociationToList(2l, pt, client2, associationList);
        when(personalTrainerService.read(1l)).thenReturn(pt);
        when(ptClientAssociationService.readAllFromPersonalTrainer(pt)).thenReturn(associationList);
        //when
        ResultActions resultActions = mockMvc.perform(get("/ptClientAssociations")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .param("personalTrainerId", "1"))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        String jsonResponse = getJsonResponseAsString(resultActions);
        ObjectMapper mapper = new ObjectMapper();
        List<PtClientAssociationDto> responseDtoList = mapper.readValue(jsonResponse, new TypeReference<List<PtClientAssociationDto>>() {
        });

        List<PtClientAssociation> actual = new ArrayList<>();
        for (PtClientAssociationDto ptClientAssociation : responseDtoList) {
            actual.add(getMapper().map(ptClientAssociation, PtClientAssociation.class));
        }
        assertEquals(associationList, actual);
    }

    @Test
    public void shouldGetAllAssociationsForClient() throws Exception {
        //given
        ClientDto clientDto = new ClientDto();
        clientDto.setId(2l);
        clientDto.setFirstName("Fat");
        clientDto.setLastName("Joe");
        PersonalTrainer pt = createPersonalTrainer();
        Client client1 = createClient(2l, "Fat", "Joe");
        List<PtClientAssociation> associationList = new ArrayList<>();
        addAssociationToList(1l, pt, client1, associationList);
        when(clientService.read(2l)).thenReturn(client1);
        when(ptClientAssociationService.readAllFromClient(client1)).thenReturn(associationList);
        //when
        ResultActions resultActions = mockMvc.perform(get("/ptClientAssociations")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .param("clientId", "2"))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        String jsonResponse = getJsonResponseAsString(resultActions);
        ObjectMapper mapper = new ObjectMapper();
        List<PtClientAssociationDto> responseDtoList = mapper.readValue(jsonResponse, new TypeReference<List<PtClientAssociationDto>>() {
        });

        List<PtClientAssociation> actual = new ArrayList<>();
        for (PtClientAssociationDto ptClientAssociation : responseDtoList) {
            actual.add(getMapper().map(ptClientAssociation, PtClientAssociation.class));
        }
        assertEquals(associationList, actual);
    }


    private PersonalTrainer createPersonalTrainer() {
        PersonalTrainer pt = new PersonalTrainer();
        pt.setId(1l);
        pt.setFirstName("John");
        pt.setLastName("Doe");
        return pt;
    }


    private Client createClient(long id, String firstName, String lastName) {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return client;
    }

    private void addAssociationToList(long id, PersonalTrainer pt, Client client, List<PtClientAssociation> associationList) {
        PtClientAssociation association = new PtClientAssociation();
        association.setId(id);
        association.setPersonalTrainer(pt);
        association.setClient(client);
        associationList.add(association);
    }

    private String getJsonResponseAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    private Mapper getMapper() {
        return DozerBeanMapperSingletonWrapper.getInstance();
    }
}