package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SessionRepository")
public interface SessionRepository extends JpaRepository<Session, Long> {

}
