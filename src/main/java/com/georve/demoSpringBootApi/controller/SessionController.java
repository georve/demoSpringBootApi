package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.error.CustomException;
import com.georve.demoSpringBootApi.error.ResourceAlreadyExists;
import com.georve.demoSpringBootApi.error.ResourceNotFoundException;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.services.SessionService;
import com.georve.demoSpringBootApi.utils.ExceptionDefinitions;
import org.springframework.beans.BeanUtils;
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
        List<Session> sessions = service.findAll();

        if (sessions == null || sessions.isEmpty()) {
            throw new CustomException(ExceptionDefinitions.EMPTY_RESOURCE);
        }

        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("{id}")
    ResponseEntity<Session> getSessionsById(@PathVariable Long id){
        Session current=service.findById(id);
        if(current==null){
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);
        }
        return new ResponseEntity<>(current, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Session> create(@RequestBody Session session) {

        if (service.exit(session)) {
            throw new ResourceAlreadyExists(ExceptionDefinitions.ALREADY_EXIST);
        }

        return new ResponseEntity<>(service.saveOrUpdate(session), HttpStatus.CREATED);
    }

    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable Long id) {
        Session current=service.findById(id);
        if(current==null){
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);

        }

        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Session> update(@PathVariable Long id, @RequestBody Session se) {

        Session current = service.findById(id);

        if (current == null) {
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);
        }

        BeanUtils.copyProperties(se,current,"session_id");

        service.saveOrUpdate(current);
        return new ResponseEntity<Session>(current, HttpStatus.OK);
    }


}
