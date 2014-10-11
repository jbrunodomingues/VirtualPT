package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.AuthenticationDto;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.service.PersonalTrainerService;
import com.brn.homebrew.service.TokenService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/rest-servlet.xml", "classpath:test-dao.xml", "classpath:test.xml"})
@WebAppConfiguration
@DirtiesContext
public class AuthenticationControllerTest {

    @Autowired
    PersonalTrainerService personalTrainerService;
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private TokenService tokenService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void shouldFailAuthenticationWithWrongCredentials() throws Exception {
        //given
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername("username");
        authenticationDto.setPassword("wrongPassword");
        String authenticationJson = new ObjectMapper().writeValueAsString(authenticationDto);
        //when
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(authenticationJson))
                .andDo(print());
        //then
        resultActions.andExpect(status().isUnauthorized());
        resultActions.andExpect(jsonPath("$.code").value(UNAUTHORIZED.value()));
        resultActions.andExpect(jsonPath("$.message").value("bad credentials"));
    }

    @Test
    public void shouldAuthenticateAndReceiveToken() throws Exception {
        //given
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername("username");
        authenticationDto.setPassword("password");
        String authenticationJson = new ObjectMapper().writeValueAsString(authenticationDto);
        when(tokenService.createTokenForUser("username")).thenReturn("123456");
        //when
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(authenticationJson))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.token").value("123456"));
    }

    @Test
    public void shouldRead() throws Exception {
        //given
        PersonalTrainer expected = createPersonalTrainer();
        when(personalTrainerService.read(1L)).thenReturn(expected);
        //when
        ResultActions resultActions = mockMvc.perform(get("/personalTrainers/{id}", 1)
                .contentType(APPLICATION_JSON)
                .header("X-Auth-Token", "123456")
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

    private String getJsonResponseAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
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
//
//    private void addClientsToPersonalTrainer(PersonalTrainer personalTrainer) {
//        Client client = new Client();
//        client.setId(2L);
//        client.setFirstName("Fat");
//        client.setLastName("Joe");
//        personalTrainer.getClients().add(client);
//        client = new Client();
//        client.setId(3L);
//        client.setFirstName("Chubby");
//        client.setLastName("Devlin");
//        personalTrainer.getClients().add(client);
//    }

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
                .header("X-Auth-Token", "123456")
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
}