package com.example.jmr.service;

import com.example.jmr.entity.Company_job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
}
