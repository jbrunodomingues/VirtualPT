package com.brn.homebrew.controller;

import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PersonalTrainerControllerTest {

    MockMvc mockMvc;
    @Mock
    PersonalTrainerService personalTrainerService;
    @InjectMocks
    PersonalTrainerController personalTrainerController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(personalTrainerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        PersonalTrainer expected = createPersonalTrainer();
        when(personalTrainerService.read(1L)).thenReturn(expected);
        //when
        ResultActions resultActions = mockMvc.perform(get("/personalTrainers/{id}", 1)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        String jsonResponse = getJsonResponseAsString(resultActions);
        ObjectMapper mapper = new ObjectMapper();
        PersonalTrainer actual = mapper.readValue(jsonResponse, PersonalTrainer.class);
        assertEquals(expected, actual);
    }

    private PersonalTrainer createPersonalTrainer() {
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        return expected;
    }

    @Test
    public void shouldReadAll() throws Exception {
        //given
        List<PersonalTrainer> expectedList = new ArrayList<>();
        PersonalTrainer personalTrainer = createPersonalTrainer();
        expectedList.add(personalTrainer);
        personalTrainer = new PersonalTrainer();
        personalTrainer.setId(2L);
        personalTrainer.setFirstName("Fat");
        personalTrainer.setLastName("Louis");
        expectedList.add(personalTrainer);
        when(personalTrainerService.readAll()).thenReturn(expectedList);
        //when
        ResultActions resultActions = mockMvc.perform(get("/personalTrainers")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        String jsonResponse = getJsonResponseAsString(resultActions);
        ObjectMapper mapper = new ObjectMapper();
        List<PersonalTrainer> actualList = mapper.readValue(jsonResponse, new TypeReference<List<PersonalTrainer>>() {
        });
        assertEquals(expectedList, actualList);
    }

    private String getJsonResponseAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }
}