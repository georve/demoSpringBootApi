package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
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

    public Session findById(Long any) {
        return respository.getOne(any);
    }

    public void deleteById(Long any) {
         respository.deleteById(any);
    }

    public Session update(Session current) {
      return respository.saveAndFlush(current);
    }

}
