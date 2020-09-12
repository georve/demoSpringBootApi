package com.georve.demoSpringBootApi.controller;


import com.georve.demoSpringBootApi.error.CustomException;
import com.georve.demoSpringBootApi.error.ResourceAlreadyExists;
import com.georve.demoSpringBootApi.error.ResourceNotFoundException;
import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.services.SpeakerService;
import com.georve.demoSpringBootApi.utils.ExceptionDefinitions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerService service;

    @GetMapping
    ResponseEntity<List<Speaker>> getAllSpeakers(){
        List<Speaker> sessions = service.findAll();
        if (sessions == null || sessions.isEmpty()) {
            throw new CustomException(ExceptionDefinitions.EMPTY_RESOURCE);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("{id}")
    ResponseEntity<Speaker> getSpeakerById(@PathVariable Long id){
        Optional<Speaker> current=service.findById(id);
        if(!current.isPresent()){
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);
        }
        return new ResponseEntity<>(current.get(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Speaker> create(@RequestBody Speaker speaker) {

        if (service.exists(speaker)) {
            throw new ResourceAlreadyExists(ExceptionDefinitions.ALREADY_EXIST);
        }
        return new ResponseEntity<>(service.saveOrUpdate(speaker), HttpStatus.CREATED);
    }

    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Speaker> current=service.findById(id);
        if(!current.isPresent()){
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);
        }

        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Speaker> update(@PathVariable Long id, @RequestBody Speaker se) {

        Optional<Speaker> current = service.findById(id);

        if (!current.isPresent()) {
            throw new ResourceNotFoundException(ExceptionDefinitions.NOT_FOUND);
        }

        BeanUtils.copyProperties(se,current,"speaker_id");

        service.saveOrUpdate(current.get());
        return new ResponseEntity<Speaker>(current.get(), HttpStatus.OK);
    }

}
