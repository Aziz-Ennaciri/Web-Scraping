package com.webscraping.Entities;

import jakarta.persistence.*;

@Entity
public class Company extends EntityBase{
    @Column(nullable = false, unique = true)
    private String companyName;
    private String ville;
    private String pays;

    public Company() {
        super();
    }

    public Company(String companyName, String ville, String pays) {
        super();
        this.companyName = companyName;
        this.ville = ville;
        this.pays = pays;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
