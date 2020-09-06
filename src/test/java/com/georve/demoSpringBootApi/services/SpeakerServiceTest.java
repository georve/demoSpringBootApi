package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpeakerServiceTest {

    @Autowired
    private SpeakerRepository repository;

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void getAllSpeaker(){
        Speaker current=this.getSpeaker();
        repository.save(current);
        SpeakerService service=new SpeakerService(repository);

        List<Speaker> list=service.findAll();
        Speaker lastSpeaker=list.get(0);

        assertEquals(current.getTitle(), lastSpeaker.getTitle());
        assertEquals(current.getCompany(), lastSpeaker.getCompany());
        assertEquals(current.getSpeaker_id(), lastSpeaker.getSpeaker_id());


    }

    @Test// verify proxy
    void getOneSpeakerById(){
        Speaker current=this.getSpeaker();
        repository.save(current);
        SpeakerService service=new SpeakerService(repository);
        //when(repository.getOne(1L)).thenReturn(this.getSession());
        Speaker lastSession=service.findById(1L);
        assertNotNull(lastSession);

    }

    @Test
    void deleteById(){
        Speaker current=this.getSpeaker();
        Speaker saved=repository.saveAndFlush(current);
        SpeakerService service=new SpeakerService(repository);
        service.deleteById(saved.getSpeaker_id());

        Double lastSession=service.count();
        assertTrue(lastSession.compareTo(0.0)==0);
    }

    @Test
    void saveASpeaker() {
        SpeakerService service=new SpeakerService(repository);
        Speaker todoSample = this.getSpeaker();
        service.saveOrUpdate(todoSample);
        assertEquals(1.0, service.count());
    }


    private Speaker getSpeaker() {
        Speaker sb=new Speaker();
        sb.setSpeaker_id(1L);
        sb.setTitle("public general");
        sb.setCompany("To everyone");
        sb.setSpeaker_bio("New Speaker");
        sb.setFirst_name("Georman");
        sb.setLast_name("Calderon");
        return sb;
    }

}
