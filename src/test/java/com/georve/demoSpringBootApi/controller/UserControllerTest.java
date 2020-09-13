package com.georve.demoSpringBootApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georve.demoSpringBootApi.error.ResourceAlreadyExists;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void createAnewUserSuccess() throws Exception {

        User eatToDo = this.getUserTosave();
        String token=this.createToken("georve");
        when(service.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.exists(any(User.class))).thenReturn(false);
        when(service.saveOrUpdate(any(User.class))).thenReturn(eatToDo);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        ResultActions result = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
                .header("Authorization", "Bearer " + token));


        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("georve"));

    }



    @Test
    void createAnewUserrepeated() throws Exception {

        String token=this.createToken("georve");
        User eatToDo = this.getUserTosave();
        when(service.findByUserName(any(String.class))).thenReturn(geUserDetails());
        when(service.exists(any(User.class))).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExists))
                .andExpect(result -> assertEquals("Resource already exists in DB.", result.getResolvedException().getMessage()));

    }

    @Test
    void loginUserNotFound() throws Exception {

        User eatToDo = this.getUserTosave();

        when(service.findByUserName(any(String.class))).thenReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



       mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Request resource is not found.", result.getResolvedException().getMessage()));

    }

    @Test
    void loginUserInvalidPassword() throws Exception {

        User eatToDo = this.getUserTosave();

        when(service.findByUserName(any(String.class))).thenReturn(geUserDetails());

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Request resource is not found.", result.getResolvedException().getMessage()));

    }


    @Test
    void loginUserSuccess() throws Exception {

        User eatToDo = this.getUserTosave();

        when(service.findByUserName(any(String.class))).thenReturn(geUserDetails());

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = null;
        try {
            eatToDoJSON = objectMapper.writeValueAsString(eatToDo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        ResultActions result = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON));


        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("absemc"));

    }

    private User getUserTosave() {
        User user=new User();
        user.setId(1l);
        user.setUsername("georve");
        user.setPassword("12345");
        return user;
    }

    private UserDetails geUserDetails(){
        User user=this.getUserTosave();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public  String createToken(String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;
    }





}
