package com.brn.homebrew.controller;

import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.impl.MappingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        ControllerTestsHelper.setDependencyUsingReflection(personalTrainerController, "mappingService", new MappingServiceImpl());
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        PersonalTrainer personalTrainer = createPersonalTrainer();
        when(personalTrainerService.read(1L)).thenReturn(personalTrainer);
        //when
        ResultActions resultActions = mockMvc.perform(get("/personalTrainers/{id}", 1)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1) )
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    private PersonalTrainer createPersonalTrainer() {
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(1L);
        expected.setFirstName("John");
        expected.setLastName("Doe");
        return expected;
    }

    private PersonalTrainer createPersonalTrainer2() {
        PersonalTrainer expected = new PersonalTrainer();
        expected.setId(2L);
        expected.setFirstName("Fit");
        expected.setLastName("Jack");
        return expected;
    }

    @Test
    public void shouldReadAll() throws Exception {
        //given
        List<PersonalTrainer> personalTrainerList = new ArrayList<>();
        personalTrainerList.add(createPersonalTrainer());
        personalTrainerList.add(createPersonalTrainer2());
        when(personalTrainerService.readAll()).thenReturn(personalTrainerList);
        //when
        ResultActions resultActions = mockMvc.perform(get("/personalTrainers")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("John"))
                .andExpect(jsonPath("$.[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].firstName").value("Fit"))
                .andExpect(jsonPath("$.[1].lastName").value("Jack"));
    }
}