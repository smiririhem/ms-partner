package com.example.mspartner.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "PartnerSolution")
public class PartnerSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String name;
    private String logo;

    public PartnerSolution(){}

    public PartnerSolution(Long id, String description, String name, String logo) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
