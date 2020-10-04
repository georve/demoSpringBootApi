package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userrepository")
public interface UserRepository extends AbstractBaseRepository<User, Long>{


    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
