package com.georve.demoSpringBootApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService service;
    /*
     first error java.lang.AssertionError: No value at JSON path “$”
     */
    @Test
    void getAllSession() throws Exception {
        List<Session> sessions=new ArrayList<Session>();
        sessions.add(this.getSession());
        when(service.findAll()).thenReturn(sessions);

        mockMvc.perform(MockMvcRequestBuilders.get("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$",hasSize(1))).andDo(print());

    }

    @Test
    void getOneSessionById() throws Exception {
        when(service.findById(any(Long.class))).thenReturn(this.getSession());

        mockMvc.perform(MockMvcRequestBuilders.get("/sessions/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.session_id").value(1L))
                .andExpect(jsonPath("$.session_name").value("public general"));
    }

    @Test
    void successfullyCreateASession() throws Exception {
        Session eatToDo = this.getSession();
        when(service.save(any(Session.class))).thenReturn(eatToDo);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResultActions result = mockMvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.session_id").value(1L))
                .andExpect(jsonPath("$.session_name").value("public general"));
    }





    private Session getSession() {
        Session sb=new Session();
        sb.setSession_id(1L);
        sb.setSession_name("public general");
        sb.setSession_description("To everyone");
        sb.setSession_length(24);
        return sb;
    }
}


