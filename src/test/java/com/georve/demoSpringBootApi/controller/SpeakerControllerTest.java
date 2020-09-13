package com.georve.demoSpringBootApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.services.SessionService;
import com.georve.demoSpringBootApi.services.SpeakerService;
import com.georve.demoSpringBootApi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
public class SpeakerControllerTest {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpeakerService service;
    
    
     
    @Test
    void getAllSpeakers() throws Exception {
        List<Speaker> sp=new ArrayList<Speaker>();
        sp.add(this.getSpeaker());
        when(userService.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.findAll()).thenReturn(sp);
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/speakers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        ).andExpect(jsonPath("$",hasSize(1))).andDo(print());

    }



    @Test
    void getOneSessionById() throws Exception {
        when(userService.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.findById(any(Long.class))).thenReturn(this.getSpeaker());
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/speakers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.first_name").value("Georman"));
    }

    @Test
    void successfullyCreateASpeaker() throws Exception {
        Speaker eatToDo = this.getSpeaker();
        when(userService.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.saveOrUpdate(any(Speaker.class))).thenReturn(eatToDo);
        String token=this.createToken("georve");
        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResultActions result = mockMvc.perform(post("/speakers")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eatToDoJSON));


        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.first_name").value("Georman"));
    }

    @Test
    void successfullyDeleteSessionById() throws Exception{
        when(userService.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.findById(any(Long.class))).thenReturn(this.getSpeaker());
        doNothing().when(service).deleteById(any(Long.class));
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.delete("/speakers/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);

    }

    @Test
    void successfullyUpdate() throws Exception{

        Speaker eatToDo = this.getSpeaker();
        when(userService.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.findById(any(Long.class))).thenReturn(this.getSpeaker());
        when(service.saveOrUpdate(any(Speaker.class))).thenReturn(this.getSpeaker());
        String token=this.createToken("georve");
        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResultActions result= mockMvc.perform(MockMvcRequestBuilders.put("/speakers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eatToDoJSON)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
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

    public  String createToken(String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;
    }

    private User getUserToCheck() {
        User user=new User();
        user.setId(1l);
        user.setUsername("georve");
        user.setPassword("12345");
        return user;
    }

    private UserDetails geUserDetails(){
        User user=this.getUserToCheck();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

}
