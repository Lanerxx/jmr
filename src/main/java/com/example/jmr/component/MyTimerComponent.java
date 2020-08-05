package com.example.jmr.component;

import com.example.jmr.service.CompanyService;
import com.example.jmr.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTimerComponent {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CompanyService companyService;

    @Scheduled(cron = "0 0 2 * * *")
    public void timingMatching(){
        studentService.getJobMatchResult();
        companyService.getStudentMatchResult();
    }
}
