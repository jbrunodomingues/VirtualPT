package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import com.brn.homebrew.service.ClientService;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.PtClientAssociationService;
import com.brn.homebrew.service.impl.MappingServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .addInterceptors()
                        .build();

        ControllerTestsHelper.setDependencyUsingReflection(ptClientAssociationController, "mappingService", new MappingServiceImpl());
    }

    @Test
    public void shouldCreateAssociation() throws Exception {
        //given
        PtClientAssociation ptClientAssociationCreated = new PtClientAssociation();
        ptClientAssociationCreated.setId(1l);
        ptClientAssociationCreated.setPersonalTrainer(createPersonalTrainer());
        ptClientAssociationCreated.setClient(createClient());
        PersonalTrainerDto personalTrainerDto = new PersonalTrainerDto();
        personalTrainerDto.setId(1l);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(2l);
        PtClientAssociationDto ptClientAssociationDto = new PtClientAssociationDto();
        ptClientAssociationDto.setClient(clientDto);
        ptClientAssociationDto.setPersonalTrainer(personalTrainerDto);
        String ptClientAssociationJson = new ObjectMapper().writeValueAsString(ptClientAssociationDto);
        when(ptClientAssociationService.create(1l, 2l)).thenReturn(1l);
        when(ptClientAssociationService.read(1l)).thenReturn(ptClientAssociationCreated);
        ResultActions resultActions = mockMvc.perform(post("/ptClientAssociations")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(ptClientAssociationJson))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.personalTrainer.id").value(1))
                .andExpect(jsonPath("$.personalTrainer.firstName").value("John"))
                .andExpect(jsonPath("$.personalTrainer.lastName").value("Doe"))
                .andExpect(jsonPath("$.client.id").value(2))
                .andExpect(jsonPath("$.client.firstName").value("Fat"))
                .andExpect(jsonPath("$.client.lastName").value("Joe"));
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        PersonalTrainer personalTrainer = createPersonalTrainer();
        Client client = createClient();
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
        Client client1 = createClient();
        Client client2 = createClient2();
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
        Client client1 = createClient();
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


    private Client createClient() {
        Client client = new Client();
        client.setId(2l);
        client.setFirstName("Fat");
        client.setLastName("Joe");
        return client;
    }

    private Client createClient2() {
        Client client = new Client();
        client.setId(3l);
        client.setFirstName("Chubby");
        client.setLastName("Becky");
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