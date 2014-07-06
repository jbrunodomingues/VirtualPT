package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.brn.homebrew.entity.Client;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.entity.PtClientAssociation;
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
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationControllerTest {

    MockMvc mockMvc;
    @Mock
    PersonalTrainerService personalTrainerService;
    @Mock
    PtClientAssociationService ptClientAssociationService;
    @InjectMocks
    PtClientAssociationController ptClientAssociationController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(ptClientAssociationController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

//    @Test
//    public void shouldCreateAssociation() throws Exception {
//        //given
//        ClientDto client = new ClientDto();
//        client.setId(2l);
//        client.setFirstName("John");
//        client.setLastName("Doe");
//        PersonalTrainerDto personalTrainer = new PersonalTrainerDto();
//        personalTrainer.setId(1l);
//        personalTrainer.setFirstName("Goncalo");
//        personalTrainer.setLastName("Mosqueira");
//        PtClientAssociationDto association = new PtClientAssociationDto();
//        association.setClient(client);
//        association.setPersonalTrainer(personalTrainer);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonObj = mapper.writeValueAsString(association);
//        //when
//        ResultActions resultActions = mockMvc.perform(post("/ptClientAssociations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(jsonObj))
//                .andDo(print());
//        //then
//        resultActions.andExpect(status().isOk());
//        verify(personalTrainerService.read(1l));
//        verify(clientService.read(2l));
//    }

    @Test
    public void shouldGetAllAssociationsForPersonalTrainer() throws Exception {
        //given
        PersonalTrainerDto ptDto = new PersonalTrainerDto();
        ptDto.setId(1l);
        ptDto.setFirstName("John");
        ptDto.setLastName("Doe");
        PersonalTrainer pt = new PersonalTrainer();
        pt.setId(1l);
        pt.setFirstName("John");
        pt.setLastName("Doe");
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

    private void addAssociationToList(long id, PersonalTrainer pt, Client client, List<PtClientAssociation> associationList) {
        PtClientAssociation association = new PtClientAssociation();
        association.setId(id);
        association.setPersonalTrainer(pt);
        association.setClient(client);
        associationList.add(association);
    }

    private Client createClient(long id, String firstName, String lastName) {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return client;
    }

    private String getJsonResponseAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    private Mapper getMapper() {
        return DozerBeanMapperSingletonWrapper.getInstance();
    }

    //    @Test
//    public void shouldReadClientsFromPersonalTrainer() throws Exception {
//        //given
//        PersonalTrainer personalTrainer = createPersonalTrainer();
//        addClientsToPersonalTrainer(personalTrainer);
//        when(personalTrainerService.read(1L)).thenReturn(personalTrainer);
//        //when
//        ResultActions resultActions = mockMvc.perform(get("/personalTrainers/{id}/clients", 1)
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON))
//                .andDo(print());
//        //then
//        resultActions.andExpect(status().isOk());
//        String jsonResponse = getJsonResponseAsString(resultActions);
//        Set<Client> expectedClients = personalTrainer.getClients();
//        ObjectMapper mapper = new ObjectMapper();
//        Set<Client> actualClients = mapper.readValue(jsonResponse, new TypeReference<Set<Client>>() {
//        });
//        assertEquals(expectedClients, actualClients);
//    }
}
