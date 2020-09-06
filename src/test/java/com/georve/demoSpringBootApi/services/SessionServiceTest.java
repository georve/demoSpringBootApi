package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class SessionServiceTest {

    @Autowired
    private SessionRepository repository;

    @Test
    void getAllSession(){
     Session current=this.getSession();
     repository.save(current);
     SessionService service=new SessionService(repository);

     List<Session> list=service.findAll();
     Session lastSession=list.get(0);

     assertEquals(current.getSession_name(), lastSession.getSession_name());
     assertEquals(current.getSession_description(), lastSession.getSession_description());
     assertEquals(current.getSession_id(), lastSession.getSession_id());


    }

    @Test
    void saveASession() {
        SessionService service=new SessionService(repository);
        Session todoSample = this.getSession();

        service.save(todoSample);

        assertEquals(1.0, service.count());
    }


    private Session getSession() {
        Session sb=new Session();
        sb.setSession_id(1L);
        sb.setSession_name("public general");
        sb.setSession_description("To everyone");
        sb.setSession_length(24);
        return sb;
    }
}
