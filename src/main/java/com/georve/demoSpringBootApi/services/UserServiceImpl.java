package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.repository.AbstractBaseRepositoryImpl;
import com.georve.demoSpringBootApi.repository.SpeakerRepository;
import com.georve.demoSpringBootApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

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
    public UserDetails findByUserName(String any) {
        User user= repository.findByUserName(any);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }
}
