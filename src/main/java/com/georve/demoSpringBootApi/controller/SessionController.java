package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.services.SessionService;
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
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("{id}")
    ResponseEntity<Session> getSessionsById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Session> create(@RequestBody Session session) {
        return new ResponseEntity<>(service.save(session), HttpStatus.CREATED);
    }

    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable Long id) {
        Session current=service.findById(id);
        if(current==null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Session> update(@PathVariable Long id, @RequestBody Session se) {

        Session current = service.findById(id);

        if (current == null) {
            return new ResponseEntity<Session>(HttpStatus.NOT_FOUND);
        }

        BeanUtils.copyProperties(se,current,"session_id");

        service.update(current);
        return new ResponseEntity<Session>(current, HttpStatus.OK);
    }


}
