package com.webscraping.Controllers;

import com.webscraping.Beans.Job;
import com.webscraping.Service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private JobServiceImpl jobService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", jobService.getAllJobs());
        return "index";
    }
//    @GetMapping("/jobs")
//    @ResponseBody
//    public String getChartData(Model model) {
//        List<Job> jobs = jobService.getAllJobs();
//
//        // Group jobs by title and count the occurrences
//        Map<String, Long> counts = jobs.stream()
//                .collect(Collectors.groupingBy(Job::getTitle, Collectors.counting()));
//
//        // Flatten the data
//        List<String> labels = new ArrayList<>(counts.keySet()); // Job titles
//        List<Long> data = new ArrayList<>(counts.values());    // Job counts
//
//        Map<String, List<Object>> response = new HashMap<>();
//        response.put("labels", Collections.singletonList(labels));  // List of job titles
//        response.put("data", Collections.singletonList(data));      // List of job counts
//
//        return response.toString();
//
//    }
}
