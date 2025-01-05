package com.webscraping.Repository;

import com.webscraping.Beans.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByTitleAndCompany(String title, String company);

    List<Job> findAllByOrderByScrapedDateDesc();
}
