package com.georve.demoSpringBootApi.repository;


import com.georve.demoSpringBootApi.model.Speaker;
import org.springframework.stereotype.Repository;

@Repository("speakerrepository")
public interface SpeakerRepository extends AbstractBaseRepository<Speaker, Long>{


}
