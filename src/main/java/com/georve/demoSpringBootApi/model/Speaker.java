package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="speakers") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Speaker extends AbstractBaseEntity {


    private String first_name;
    private String last_name;
    private String title;
    private String company;
    private String speaker_bio;

    @Lob
   // @Type(type="org.hibernate.type.BinaryType")
    @Type(type="org.hibernate.type.ImageType")
    private byte[] speaker_photo;

    @JsonIgnore
    @ManyToMany(mappedBy="speakers")
    private List<Session> sessions;

    @JsonIgnore
    @ManyToMany(mappedBy="speakers")
    private List<Workshops> workshops;

    public Speaker(){}


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSpeaker_bio() {
        return speaker_bio;
    }

    public void setSpeaker_bio(String speaker_bio) {
        this.speaker_bio = speaker_bio;
    }

    public byte[] getSpeaker_photo() {
        return speaker_photo;
    }

    public void setSpeaker_photo(byte[] speaker_photo) {
        this.speaker_photo = speaker_photo;
    }


    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Workshops> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshops> workshops) {
        this.workshops = workshops;
    }

}
