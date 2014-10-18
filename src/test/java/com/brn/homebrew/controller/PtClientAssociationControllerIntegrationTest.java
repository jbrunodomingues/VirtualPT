package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.ClientDto;
import com.brn.homebrew.controller.dto.PersonalTrainerDto;
import com.brn.homebrew.controller.dto.PtClientAssociationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Bruno Domingues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/rest-servlet.xml",
        "classpath:test-dao.xml",
        "classpath:test.xml"})
@WebAppConfiguration
@DirtiesContext
public class PtClientAssociationControllerIntegrationTest {

    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRespondErrorForInvalidIds() throws Exception {
        //given
        PersonalTrainerDto personalTrainerDto = new PersonalTrainerDto();
        personalTrainerDto.setId(1l);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(null);
        PtClientAssociationDto ptClientAssociationDto = new PtClientAssociationDto();
        ptClientAssociationDto.setClient(clientDto);
        ptClientAssociationDto.setPersonalTrainer(personalTrainerDto);
        String ptClientAssociationJson = new ObjectMapper().writeValueAsString(ptClientAssociationDto);
        ResultActions resultActions = mockMvc.perform(post("/ptClientAssociations")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(ptClientAssociationJson))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("bad request"));
    }
}