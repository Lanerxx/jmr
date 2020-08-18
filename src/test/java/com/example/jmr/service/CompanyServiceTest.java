package com.example.jmr.service;

import com.example.jmr.entity.Company_job;
import com.example.jmr.entity.Job_match_result;
import com.example.jmr.entity.Student_match_result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    public void getStudentMatchResultTest(){
        companyService.getStudentMatchResult();
    }

    @Test
    public void getCompanyJobByCompanyAndJobTest(){
        Company_job company_job= companyService.getCompanyJobByCompanyAndJob(1, 9);
        log.debug("{}", company_job.toString());
    }

    @Test
    public void getCompanyJobsTest(){
        List<Company_job> company_jobs = companyService.getCompanyJobs("系统分析员");
        company_jobs.forEach(companyJob -> {
            log.debug("{}",companyJob.getCompany_job_pk().getCompany().getC_name());
        });
    }

    @Test
    public void getJobMatchResultTestFocus(){
        Map<String,Integer> focus = new HashMap<>();
        focus.put("sex", 1);
        focus.put("level", 1);
        focus.put("profession", 1);
        focus.put("history", 1);
        focus.put("language", 0);
        focus.put("range", 1);
        focus.put("position", 1);
        focus.put("city", 1);
        List<Student_match_result> studentMatchResults = companyService.getStudentMatchResult(focus, 1);
        studentMatchResults.forEach(studentMatchResult -> {
            log.debug("{} / {}", studentMatchResult.getSmr_student().getS_name(),studentMatchResult.getSmr_value());
        });
    }
}
