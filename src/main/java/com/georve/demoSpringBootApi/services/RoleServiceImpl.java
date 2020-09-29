package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.ERole;
import com.georve.demoSpringBootApi.model.Role;
import com.georve.demoSpringBootApi.repository.AbstractBaseRepositoryImpl;
import com.georve.demoSpringBootApi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractBaseRepositoryImpl<Role, Long>
        implements RoleService{

    @Autowired
    @Qualifier("rolerepository")
    private RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository){
        super(repository);
    }
    @Override
    public Optional<Role> findByName(ERole name) {
        return repository.findByName(name);
    }
}
