package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SpeakerServiceImpl extends AbstractBaseRepositoryImpl<Speaker, Long>
        implements SpeakerService{

    private SpeakerRepository repository;

    public SpeakerServiceImpl(SpeakerRepository repository){
        super(repository);
    }

}
