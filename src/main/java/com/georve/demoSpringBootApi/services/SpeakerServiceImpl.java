package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.repository.AbstractBaseRepositoryImpl;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SpeakerServiceImpl extends AbstractBaseRepositoryImpl<Speaker, Long>
        implements SpeakerService{

    @Autowired
    @Qualifier("speakerrepository")
    private SpeakerRepository repository;

    public SpeakerServiceImpl(SpeakerRepository repository){
        super(repository);
    }

}
