package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService service;

    @GetMapping
    ResponseEntity<List<Session>> getAllSessions(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Session> getSessionsById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Session> create(@RequestBody Session session) {
        return new ResponseEntity<>(service.save(session), HttpStatus.CREATED);
    }
}
