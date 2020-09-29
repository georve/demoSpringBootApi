package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userrepository")
public interface UserRepository extends AbstractBaseRepository<User, Long>{

    @Query(value="Select u from User u where u.username=:username")
    Optional<User> findByUserName(@Param("username") String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
