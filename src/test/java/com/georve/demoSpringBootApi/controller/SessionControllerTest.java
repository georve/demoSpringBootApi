package com.georve.demoSpringBootApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georve.demoSpringBootApi.config.Profiles;
import com.georve.demoSpringBootApi.error.CustomException;
import com.georve.demoSpringBootApi.error.ResourceNotFoundException;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.services.SessionService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class SessionControllerTest {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @MockBean
    private UserService userService;

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
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        ).andExpect(jsonPath("$",hasSize(1))).andDo(print());

    }

    @Test
    public void getAllSession_empty() throws Exception {
        List<Session> sessions=new ArrayList<Session>();
        when(service.findAll()).thenReturn(sessions);
       String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomException))
                .andExpect(result -> assertEquals("Resource is empty in DB.", result.getResolvedException().getMessage()));
    }

    @Test
    void getOneSessionById() throws Exception {
        when(service.findById(any(Long.class))).thenReturn(this.getSession());
        Integer param = 1;
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/sessions/{id}",param)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.session_name").value("public general"));
    }

    @Test
    public void getOneSession_empty() throws Exception {
        when(service.findById(any(Long.class))).thenReturn(null);
        Integer param = 1;
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.get("/sessions/{id}",param)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Request resource is not found.", result.getResolvedException().getMessage()));
    }

    @Test
    void successfullyCreateASession() throws Exception {
        Session eatToDo = this.getSession();
        when(service.saveOrUpdate(any(Session.class))).thenReturn(eatToDo);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token=this.createToken("georve");
        ResultActions result = mockMvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
                .header("Authorization", "Bearer " + token));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.session_name").value("public general"));
    }

    @Test
    void successfullyDeleteSessionById() throws Exception{
        when(service.findById(any(Long.class))).thenReturn(this.getSession());
        doNothing().when(service).deleteById(any(Long.class));
        String token=this.createToken("georve");
        mockMvc.perform(MockMvcRequestBuilders.delete("/sessions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);

    }

    @Test
    void successfullyUpdate() throws Exception{

        Session eatToDo = this.getSession();
        when(service.findById(any(Long.class))).thenReturn(this.getSession());
        when(service.saveOrUpdate(any(Session.class))).thenReturn(this.getSession());
        String token=this.createToken("georve");
        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResultActions result= mockMvc.perform(MockMvcRequestBuilders.put("/sessions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
                .header("Authorization", "Bearer " + token)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.session_name").value("public general"));


    }





    private Session getSession() {
        Session sb=new Session();
        sb.setId(1L);
        sb.setSession_name("public general");
        sb.setSession_description("To everyone");
        sb.setSession_length(24);
        return sb;
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


