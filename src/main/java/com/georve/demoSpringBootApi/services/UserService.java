package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends AbstractBaseService<User, Long> {

    UserDetails findByUserName(String any);
}
