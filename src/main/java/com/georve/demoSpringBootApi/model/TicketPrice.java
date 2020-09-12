package com.georve.demoSpringBootApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="ticket_prices") // the table in the database tht will contain our cars data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TicketPrice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ticket_price_id;
    private Double base_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_type_code")
    private TicketTypes ticketType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pricing_category_code")
    private PricingCategory pricingCategory;

}
