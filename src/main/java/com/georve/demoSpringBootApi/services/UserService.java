package com.georve.demoSpringBootApi.services;


import com.georve.demoSpringBootApi.model.User;


public interface UserService extends AbstractBaseService<User, Long> {

    User findByUserName(String any);
}
