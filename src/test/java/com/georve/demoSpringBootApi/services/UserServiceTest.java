package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.Session;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository repository;

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void saveAUser() {
        UserService service=new UserServiceImpl(repository);
        User userSample = this.getUser();
        service.saveOrUpdate(userSample);
        assertEquals(1.0, service.count());
    }

    private User getUser() {
        User user=new User();
        user.setUsername("georve");
        user.setPassword("absc");
        return user;
    }

}
