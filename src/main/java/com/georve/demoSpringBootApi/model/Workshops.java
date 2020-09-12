package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="workshops") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Workshops {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long workshop_id;
    private String workshop_name;
    private String description;
    private String requirements;
    private String room;
    private Integer capacity;

    @ManyToMany
    @JoinTable(
            name="workshop_speakers",
            joinColumns = @JoinColumn(name="workshop_id"),
            inverseJoinColumns = @JoinColumn(name="speaker_id"))
    private List<Speaker> speakers;

    public Workshops(){}

    public Long getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(Long workshop_id) {
        this.workshop_id = workshop_id;
    }

    public String getWorkshop_name() {
        return workshop_name;
    }

    public void setWorkshop_name(String workshop_name) {
        this.workshop_name = workshop_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
