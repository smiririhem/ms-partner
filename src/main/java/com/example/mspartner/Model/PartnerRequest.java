package com.example.mspartner.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "partner-request")
public class PartnerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private State state = State.New ;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public PartnerRequest(Long id, String date, Partner partner) {
        this.id = id;
        this.date = date;
        this.state = State.New;
        this.partner = partner;
    }
    public PartnerRequest(String date, Partner partner) {

        this.date = date;
        this.state = State.New;
        this.partner = partner;
    }

    public PartnerRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
