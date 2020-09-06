package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    private SessionRepository respository;

    public SessionService(SessionRepository repository) {
        this.respository=repository;
    }


    public List<Session> findAll() {
        return respository.findAll();
    }

    public Session save(Session any) {
        return respository.save(any);
    }

    public double count() {
        return respository.count();
    }
}
