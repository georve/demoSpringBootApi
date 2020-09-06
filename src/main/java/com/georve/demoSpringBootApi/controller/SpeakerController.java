package com.georve.demoSpringBootApi.controller;


import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.services.SpeakerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerService service;

    @GetMapping
    ResponseEntity<List<Speaker>> getAllSpeakers(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("{id}")
    ResponseEntity<Speaker> getSpeakerById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Speaker> create(@RequestBody Speaker speaker) {
        return new ResponseEntity<>(service.saveOrUpdate(speaker), HttpStatus.CREATED);
    }

    @RequestMapping(value="{id}",method=RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable Long id) {
        Speaker current=service.findById(id);
        if(current==null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Speaker> update(@PathVariable Long id, @RequestBody Speaker se) {

        Speaker current = service.findById(id);

        if (current == null) {
            return new ResponseEntity<Speaker>(HttpStatus.NOT_FOUND);
        }

        BeanUtils.copyProperties(se,current,"speaker_id");

        service.saveOrUpdate(current);
        return new ResponseEntity<Speaker>(current, HttpStatus.OK);
    }

}
