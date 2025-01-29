package com.webscraping.Repository;

import com.webscraping.Entities.Company;
import com.webscraping.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByTitleAndCompany(String title, Company company);

    List<Job> findAllByOrderByScrapedDateDesc();
}
