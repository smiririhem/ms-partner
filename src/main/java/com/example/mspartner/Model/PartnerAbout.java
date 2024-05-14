package com.example.mspartner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "PartnerAbout")
public class PartnerAbout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cumulusExperience;
    private String otherPartners;
    private String reasons;
    private String logo;
    private String file;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public PartnerAbout(Long id, String cumulusExperience, String otherPartners, String reasons, String logo, String file, Partner partner) {
        this.id = id;
        this.cumulusExperience = cumulusExperience;
        this.otherPartners = otherPartners;
        this.reasons = reasons;
        this.logo = logo;
        this.file = file;
        this.partner = partner;
    }

    public PartnerAbout(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCumulusExperience() {
        return cumulusExperience;
    }

    public void setCumulusExperience(String cumulusExperience) {
        this.cumulusExperience = cumulusExperience;
    }

    public String getOtherPartners() {
        return otherPartners;
    }

    public void setOtherPartners(String otherPartners) {
        this.otherPartners = otherPartners;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
