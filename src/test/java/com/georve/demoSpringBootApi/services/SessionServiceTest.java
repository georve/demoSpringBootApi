package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@SpringBootTest
public class SessionServiceTest {

    @Autowired
    private SessionRepository repository;

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void getAllSession(){
     Session current=this.getSession();
     repository.save(current);
     SessionService service=new SessionServiceImpl(repository);

     List<Session> list=service.findAll();
     Session lastSession=list.get(0);

     assertEquals(current.getSession_name(), lastSession.getSession_name());
     assertEquals(current.getSession_description(), lastSession.getSession_description());
     assertEquals(current.getId(), lastSession.getId());


    }

    @Test// verify proxy
    void getOneSessionById(){
        Session current=this.getSession();
        repository.save(current);
        SessionService service=new SessionServiceImpl(repository);
        //when(repository.getOne(1L)).thenReturn(this.getSession());
        Session lastSession=service.findById(1L);
        assertNotNull(lastSession);

    }

    @Test
    void deleteById(){
        Session current=this.getSession();
        Session saved=repository.saveAndFlush(current);
        SessionService service=new SessionServiceImpl(repository);
        service.deleteById(saved.getId());

        Double lastSession=service.count();
        assertTrue(lastSession.compareTo(0.0)==0);
    }

    @Test
    void saveASession() {
        SessionService service=new SessionServiceImpl(repository);
        Session todoSample = this.getSession();
        service.saveOrUpdate(todoSample);
        assertEquals(1.0, service.count());
    }


    private Session getSession() {
        Session sb=new Session();
        sb.setId(1L);
        sb.setSession_name("public general");
        sb.setSession_description("To everyone");
        sb.setSession_length(24);
        return sb;
    }
}
