package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionController {
    @Autowired
    private SessionService service;

    @GetMapping("/sessions")
    ResponseEntity<List<Session>> getAllSessions(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/sessions")
    ResponseEntity<Session> create(@RequestBody Session session) {
        return new ResponseEntity<>(service.save(session), HttpStatus.CREATED);
    }
}
