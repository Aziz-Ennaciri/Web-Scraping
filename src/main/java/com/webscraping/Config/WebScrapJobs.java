package com.webscraping.Config;

import com.webscraping.Beans.Job;
import com.webscraping.Exceptions.ScrapingException;
import com.webscraping.Repository.JobRepository;
import com.webscraping.Service.JobServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebScrapJobs {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Value("${scraper.base-url}")
    private String baseUrl;

    @Value("${scraper.user-agent}")
    private String userAgent;

    @Value("${scraper.timeout}")
    private int timeout;

    @Autowired
    private JobRepository jobRepository;

    @Scheduled(cron = "${scraper.schedule}")
    public void scrapeJobs() {
        logger.info("Starting job scraping at {}", LocalDateTime.now());
        List<Job> newJobs = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(baseUrl)
                    .userAgent(userAgent)
                    .timeout(timeout)
                    .get();

            Elements jobCards = doc.select(".card.card-job");

            for (Element job : jobCards) {
                try {
                    Job jobPost = parseJobPost(job);
                    if (isValidJobPost(jobPost)) {
                        Optional<Job> existingJob = jobRepository
                                .findByTitleAndCompany(jobPost.getTitle(), jobPost.getCompany());

                        if (existingJob.isEmpty()) {
                            jobPost.setScrapedDate(LocalDateTime.now());
                            newJobs.add(jobPost);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error parsing job card: {}", e.getMessage());
                }
            }

            if (!newJobs.isEmpty()) {
                jobRepository.saveAll(newJobs);
                logger.info("Saved {} new job posts", newJobs.size());
            }

        } catch (IOException e) {
            logger.error("Failed to scrape jobs: {}", e.getMessage());
            throw new ScrapingException("Failed to scrape jobs", e);
        }
    }
    private Job parseJobPost(Element job) {
        Job jobPost = new Job();
        Elements jobCards = job.select(".card-job-detail");
        Elements e = jobCards.select("a");
        String title  = e.get(0).text().trim();
        jobPost.setTitle(title);

        String company  = e.get(1).text().trim();
        jobPost.setCompany(company);

        Elements descriptionElement = jobCards.select(".card-job-description");
        String description = descriptionElement.select("p").text().trim();
        jobPost.setDescription(description);


        String location = jobCards.select("ul").select("li").get(3).text().trim();
        jobPost.setLocation(location);
        return jobPost;
    }

    private boolean isValidJobPost(Job jobPost) {
        return jobPost.getTitle() != null && !jobPost.getTitle().isEmpty() &&
                jobPost.getCompany() != null && !jobPost.getCompany().isEmpty();
    }
}
