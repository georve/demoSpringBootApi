package com.georve.demoSpringBootApi.repository;


import com.georve.demoSpringBootApi.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SpeakerRepository")
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
