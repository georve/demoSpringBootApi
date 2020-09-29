package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.repository.AbstractBaseRepositoryImpl;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import com.georve.demoSpringBootApi.repository.UserRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends AbstractBaseRepositoryImpl<User, Long>
        implements UserService{

    @Autowired
    @Qualifier("userrepository")
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository){
        super(repository);
    }


    @Override
    public Optional<User> findByUserName(String any) {
        return repository.findByUserName(any);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUserName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
