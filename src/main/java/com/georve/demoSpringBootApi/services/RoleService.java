package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.ERole;
import com.georve.demoSpringBootApi.model.Role;

import java.util.Optional;

public interface RoleService extends AbstractBaseService<Role, Long> {

    Optional<Role> findByName(ERole name);
}
