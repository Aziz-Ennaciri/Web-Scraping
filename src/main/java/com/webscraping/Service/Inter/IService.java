package com.webscraping.Service.Inter;

import com.webscraping.Beans.Job;

import java.util.List;
import java.util.Optional;

public interface IService {

    List<Job> getLatestJobs();
    Optional<Job> findJobById(Long id);
    void deleteJob(Long id);
    Job updateJob(Job jobPost);
    List<Job> getAllJobs();
}
