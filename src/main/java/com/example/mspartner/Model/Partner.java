package com.example.mspartner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String companySize;
    private String country;
    private String city;
    private String zipCode;
    private String activityArea;
    private String date;

    private boolean display;

    @OneToOne(mappedBy = "partner", cascade = CascadeType.ALL)
    private PartnerAbout partnerAbout;

    @OneToOne(mappedBy = "partner", cascade = CascadeType.ALL)
    private PartnerContact partnerContact;
    @OneToOne(mappedBy = "partner", cascade = CascadeType.ALL)
    private PartnerRequest partnerRequest;

    @ManyToOne
    @JoinColumn(name = "partnerLevelId") // Assuming the column name in the DB is partnerLevelId
    private PartnerLevel partnerLevel;
    public Partner(){}

    public Partner(Long id, String companyName, String companySize, String country, String city, String zipCode, String activityArea, PartnerAbout partnerAbout, PartnerLevel partnerLevel, PartnerContact partnerContact, PartnerRequest partnerRequest,String date) {
        this.id = id;
        this.companyName = companyName;
        this.companySize = companySize;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.activityArea = activityArea;
        this.partnerAbout = partnerAbout;
        this.partnerLevel = partnerLevel;
        this.partnerContact = partnerContact;
        this.partnerRequest = partnerRequest;
        this.display=false;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(String activityArea) {
        this.activityArea = activityArea;
    }

    public PartnerAbout getPartnerAbout() {
        return partnerAbout;
    }

    public void setPartnerAbout(PartnerAbout partnerAbout) {
        this.partnerAbout = partnerAbout;
    }

    public PartnerLevel getPartnerLevel() {
        return partnerLevel;
    }

    public void setPartnerLevel(PartnerLevel partnerLevel) {
        this.partnerLevel = partnerLevel;
    }

    public PartnerContact getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(PartnerContact partnerContact) {
        this.partnerContact = partnerContact;
    }

    public PartnerRequest getPartnerRequest() {
        return partnerRequest;
    }
    public void setPartnerRequest(PartnerRequest partnerRquest) {
        this.partnerRequest = partnerRquest;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
