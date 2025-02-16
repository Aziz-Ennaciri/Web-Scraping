package com.webscraping;

import com.webscraping.Config.WebScrapJobs;
import com.webscraping.Controllers.HomeController;
import com.webscraping.Controllers.JobController;
import com.webscraping.Entities.Company;
import com.webscraping.Entities.Job;
import com.webscraping.Repository.CompanyRepository;
import com.webscraping.Service.JobServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WebScrapingApplicationTests  {

    @InjectMocks
    private WebScrapJobs webScrapJobs;

    @InjectMocks
    private HomeController homeController;

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobServiceImpl jobService;


    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHomeController_ReturnsIndexPage() {
        when(jobService.getAllJobs()).thenReturn(List.of());
        String viewName = homeController.home(model);
        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute(eq("items"), anyList());
    }

    @Test
    public void testJobController_ShowJobsChart_ReturnsJobsView() {
        String viewName = jobController.showJobsChart();
        assertEquals("Jobs", viewName);
    }

    @Test
    public void testJobController_GetJobStats_WithNullValues() {
        Job job1 = new Job(null, new Company("TechCorp", null, null), "Casablanca", "Job description", LocalDateTime.now());
        Job job2 = new Job("Python Developer", new Company("SoftInc", null, null), null, "Job description", LocalDateTime.now());

        List<Job> validJobs = List.of(job1, job2).stream()
                .filter(job -> job.getTitle() != null && job.getLocation() != null)
                .collect(Collectors.toList());

        when(jobService.getAllJobs()).thenReturn(validJobs);

        Map stats = jobController.getJobStats();

        assertNotNull(stats);
        assertTrue(stats.containsKey("jobTitles"));
        assertTrue(stats.containsKey("jobCounts"));
        assertTrue(stats.containsKey("locations"));
        assertTrue(stats.containsKey("locationCounts"));

        Set jobTitles = new HashSet<>((Collection) stats.get("jobTitles"));
        Set locations = new HashSet<>((Collection) stats.get("locations"));

        assertEquals(Set.of(), jobTitles);
        assertEquals(Set.of(), locations);
    }



    @Test
    public void testParseJobPost_ValidJobCard() {
        when(companyRepository.findByCompanyName("AFRICASHORE"))
                .thenReturn(Optional.of(new Company("AFRICASHORE", null, null)));

        String mockHtml = """
            <div class="card card-job">
                <div class="card-job-detail">
                    <a href="/offre-emploi-maroc/laravel-developer-mf-8251948">LARAVEL Developer (M/F)</a>
                    <a href="/recruteur/7262868" class="card-job-company company-name">AFRICASHORE</a>
                    <div class="card-job-description">
                        <p>Develop, test, and maintain applications to specified designs & standards in Laravel.</p>
                    </div>
                    <ul>
                        <li>Région de : <strong>Casablanca</strong></li>
                    </ul>
                </div>
            </div>
        """;

        Element mockElement = Jsoup.parse(mockHtml).selectFirst(".card-job");
        Job result = webScrapJobs.parseJobPost(mockElement);

        assertNotNull(result);
        assertEquals("LARAVEL Developer (M/F)", result.getTitle());
        assertEquals("AFRICASHORE", result.getCompany().getCompanyName());
        assertEquals("Casablanca", result.getLocation());
    }

    @Test
    public void testIsValidJobPost_ValidJob() {
        Job job = new Job();
        Company company = new Company("AFRICASHORE", null, null);

        job.setTitle("LARAVEL Developer (M/F)");
        job.setCompany(company);

        assertNotNull(job.getCompany(), "Company should not be null");
        assertNotNull(job.getCompany().getCompanyName(), "Company name should not be null");
        assertTrue(webScrapJobs.isValidJobPost(job), "Job should be considered valid");
    }


    @Test
    public void testIsValidJobPost_InvalidJob() {
        Job job = new Job("", new Company("", null, null), "", "", LocalDateTime.now());
        assertFalse(webScrapJobs.isValidJobPost(job));
    }
}
