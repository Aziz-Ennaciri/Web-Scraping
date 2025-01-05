package com.webscraping.Service;

import com.webscraping.Beans.Job;
import com.webscraping.Exceptions.ScrapingException;
import com.webscraping.Repository.JobRepository;
import com.webscraping.Service.Inter.IService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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