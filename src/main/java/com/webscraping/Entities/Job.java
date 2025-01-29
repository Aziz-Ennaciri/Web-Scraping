package com.webscraping.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private long id;
    private String title;
    @Column(length = 5000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;
    private String location;
    private String link;
    private LocalDateTime scrapedDate;


    public Job() {
    }

    public Job(long id, String title, String description, String location, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getScrapedDate() {
        return scrapedDate;
    }

    public void setScrapedDate(LocalDateTime scrapedDate) {
        this.scrapedDate = scrapedDate;
    }
}
