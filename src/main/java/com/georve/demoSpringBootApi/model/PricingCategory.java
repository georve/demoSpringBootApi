package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pricing_categories") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class PricingCategory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String pricing_category_code;
    private String pricing_category_name;
    private Date pricing_start_date;
    private Date pricing_end_date;


    @OneToMany(mappedBy ="pricingcategory" )
    private List<TicketPrice> tickets=new ArrayList<>();

    public PricingCategory(){}

    public String getPricing_category_code() {
        return pricing_category_code;
    }

    public void setPricing_category_code(String pricing_category_code) {
        this.pricing_category_code = pricing_category_code;
    }

    public String getPricing_category_name() {
        return pricing_category_name;
    }

    public void setPricing_category_name(String pricing_category_name) {
        this.pricing_category_name = pricing_category_name;
    }

    public Date getPricing_start_date() {
        return pricing_start_date;
    }

    public void setPricing_start_date(Date pricing_start_date) {
        this.pricing_start_date = pricing_start_date;
    }

    public Date getPricing_end_date() {
        return pricing_end_date;
    }

    public void setPricing_end_date(Date pricing_end_date) {
        this.pricing_end_date = pricing_end_date;
    }

    public List<TicketPrice> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketPrice> tickets) {
        this.tickets = tickets;
    }
}
