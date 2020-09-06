package com.georve.demoSpringBootApi.model;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="speakers") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
public class Speaker {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long speaker_id;
    private String first_name;
    private String last_name;
    private String title;
    private String company;
    private String speaker_bio;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] speaker_photo;

    @ManyToMany(mappedBy="speakers")
    private List<Session> sessions;

    public Speaker(){}

    public Long getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(Long speaker_id) {
        this.speaker_id = speaker_id;
    }

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
}
