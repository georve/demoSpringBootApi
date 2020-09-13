package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.repository.AbstractBaseRepositoryImpl;
import com.georve.demoSpringBootApi.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SessionServiceImpl extends AbstractBaseRepositoryImpl<Session, Long>
        implements SessionService{

    @Autowired
    @Qualifier("sessionrepository")
    private SessionRepository repository;

    public SessionServiceImpl(SessionRepository repository){
        super(repository);
    }
}
