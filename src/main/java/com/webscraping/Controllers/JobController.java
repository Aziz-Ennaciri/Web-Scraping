package com.webscraping.Controllers;

import com.webscraping.Beans.Job;
import com.webscraping.Service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class JobController {
    @Autowired
    private JobServiceImpl jobService;

    @GetMapping("/jobs")
    public String showJobsChart() {
        return "jobs";
    }

    @GetMapping("/api/job-stats")
    @ResponseBody
    public Map<String, Object> getJobStats() {
        List<Job> jobs = jobService.getAllJobs();

        Map<String, Long> jobCounts = jobs.stream()
                .collect(Collectors.groupingBy(Job::getTitle, Collectors.counting()));

        Map<String, Long> locationCounts = jobs.stream()
                .collect(Collectors.groupingBy(Job::getLocation, Collectors.counting()));

        Map<String, Object> stats = new HashMap<>();
        stats.put("jobTitles", jobCounts.keySet());
        stats.put("jobCounts", jobCounts.values());
        stats.put("locations", locationCounts.keySet());
        stats.put("locationCounts", locationCounts.values());

        return stats;
    }
}
