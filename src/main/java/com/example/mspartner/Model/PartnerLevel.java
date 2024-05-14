package com.example.mspartner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PartnerLevel")
public class PartnerLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String color;
    //
    @JsonIgnore
    @OneToMany(mappedBy = "partnerLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partner> partners;


    public PartnerLevel(Long id, String name, String description, String color, List<Partner> partners) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.partners = partners;
    }

    public PartnerLevel(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
}
