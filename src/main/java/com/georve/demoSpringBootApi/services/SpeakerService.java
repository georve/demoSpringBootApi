package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerService {

    @Autowired
    private SpeakerRepository respository;

    public SpeakerService(SpeakerRepository repository){
        this.respository=repository;
    }

    public List<Speaker> findAll() {
        return respository.findAll();
    }

    public Speaker findById(Long any) {
        return respository.getOne(any);
    }

    public Speaker saveOrUpdate(Speaker any) {
        return respository.saveAndFlush(any);
    }

    public void deleteById(Long any) {
        respository.deleteById(any);
    }

    public double count() {
        return respository.count();
    }
}
