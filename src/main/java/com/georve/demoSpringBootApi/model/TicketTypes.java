package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ticket_types") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TicketTypes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String ticket_type_code;
    private String ticket_type_name;
    private String description;
    private Boolean includes_workshop;

    @OneToMany(mappedBy ="ticketTypes" )
    private List<TicketPrice> tickets=new ArrayList<>();

    public TicketTypes(){}

    public String getTicket_type_code() {
        return ticket_type_code;
    }

    public void setTicket_type_code(String ticket_type_code) {
        this.ticket_type_code = ticket_type_code;
    }

    public String getTicket_type_name() {
        return ticket_type_name;
    }

    public void setTicket_type_name(String ticket_type_name) {
        this.ticket_type_name = ticket_type_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIncludes_workshop() {
        return includes_workshop;
    }

    public void setIncludes_workshop(Boolean includes_workshop) {
        this.includes_workshop = includes_workshop;
    }
}
