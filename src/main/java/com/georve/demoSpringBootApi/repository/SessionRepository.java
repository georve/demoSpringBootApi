package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.Session;
import org.springframework.stereotype.Repository;

@Repository("sessionrepository")
public interface SessionRepository extends AbstractBaseRepository<Session, Long> {

}
