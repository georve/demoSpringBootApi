package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService extends AbstractBaseService<User, Long> {

    Optional<User> findByUserName(String any);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
