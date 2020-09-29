package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.ERole;
import com.georve.demoSpringBootApi.model.Role;

import java.util.Optional;

public interface RoleRepository extends AbstractBaseRepository<Role, Long>{

    Optional<Role> findByName(ERole name);
}
