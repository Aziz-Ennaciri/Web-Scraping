package com.webscraping.Service;

import com.webscraping.Entities.Job;
import com.webscraping.Repository.JobRepository;
import com.webscraping.Service.Inter.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements IService {

    @Autowired
    private JobRepository jobRepository;


    @Override
    public List<Job> getLatestJobs() {
        return jobRepository.findAllByOrderByScrapedDateDesc();
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public Job updateJob(Job jobPost) {
        return jobRepository.save(jobPost);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}