package com.georve.demoSpringBootApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.services.SessionService;
import com.georve.demoSpringBootApi.services.SpeakerService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
public class SpeakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpeakerService service;
    
    
     
    @Test
    void getAllSpeakers() throws Exception {
        List<Speaker> sp=new ArrayList<Speaker>();
        sp.add(this.getSpeaker());
        when(service.findAll()).thenReturn(sp);

        mockMvc.perform(MockMvcRequestBuilders.get("/speakers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$",hasSize(1))).andDo(print());

    }



    @Test
    void getOneSessionById() throws Exception {
        when(service.findById(any(Long.class))).thenReturn(Optional.of(this.getSpeaker()));

        mockMvc.perform(MockMvcRequestBuilders.get("/speakers/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.speaker_id").value(1L))
                .andExpect(jsonPath("$.first_name").value("Georman"));
    }

    @Test
    void successfullyCreateASpeaker() throws Exception {
        Speaker eatToDo = this.getSpeaker();
        when(service.saveOrUpdate(any(Speaker.class))).thenReturn(eatToDo);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResultActions result = mockMvc.perform(post("/speakers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON));


        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.speaker_id").value(1L))
                .andExpect(jsonPath("$.first_name").value("Georman"));
    }

    @Test
    void successfullyDeleteSessionById() throws Exception{

        when(service.findById(any(Long.class))).thenReturn(Optional.of(this.getSpeaker()));
        doNothing().when(service).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/speakers/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);

    }

    @Test
    void successfullyUpdate() throws Exception{

        Speaker eatToDo = this.getSpeaker();

        when(service.findById(any(Long.class))).thenReturn(Optional.of(this.getSpeaker()));
        when(service.saveOrUpdate(any(Speaker.class))).thenReturn(this.getSpeaker());

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResultActions result= mockMvc.perform(MockMvcRequestBuilders.put("/speakers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.speaker_id").value(1L))
                .andExpect(jsonPath("$.first_name").value("Georman"));


    }

    private Speaker getSpeaker() {
        Speaker value=new Speaker();
        value.setFirst_name("Georman");
        value.setLast_name("Calderon");
        value.setCompany("georve");
        value.setSpeaker_bio("nueva biografia");
        value.setId(1L);
        value.setTitle("titulo_1");
        return value;
    }

}
