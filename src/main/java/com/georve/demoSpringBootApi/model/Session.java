package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sessions") // the table in the database tht will contain our cars data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@EntityListeners(AuditingEntityListener.class)
public class Session extends AbstractBaseEntity {


    private String session_name;
    private String session_description;
    private Integer session_length;

    //relationShips
    @ManyToMany
    @JoinTable(
            name="session_speakers",
            joinColumns = @JoinColumn(name="session_id"),
            inverseJoinColumns = @JoinColumn(name="speaker_id"))
    private List<Speaker> speakers;

     public Session(){

     }


    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public Integer getSession_length() {
        return session_length;
    }

    public void setSession_length(Integer session_length) {
        this.session_length = session_length;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
