package com.example.jmr.service;

import com.example.jmr.entity.Jmr_base;
import com.example.jmr.entity.Job_match_result;
import com.example.jmr.entity.Student_match_result;
import com.example.jmr.repository.JmrBaseRepository;
import com.example.jmr.repository.JobMatchResultRepository;
import com.example.jmr.repository.StudentMatchResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class StudentServiceTest {
    @Autowired
    private JmrBaseRepository jmrBaseRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JobMatchResultRepository jobMatchResultRepository;

    @Test
    public void saveTest(){
        Jmr_base jmr_base = new Jmr_base();
        jmr_base.setJmr_history_value(1);
        jmr_base.setJmr_city_value(1);
        jmrBaseRepository.save(jmr_base);
        log.debug("{}", jmr_base.getJmr_b_id());
    }

    @Test
    public void toBinaryString(){
        boolean q = true;
        int i;

        int n  = 10;
        String studentLanguage = Integer.toBinaryString(n);
        int studentLanguageN = studentLanguage.length();
        log.debug("{} /{} ", studentLanguage,studentLanguageN);

        int m  = 8;
        String jobLanguage = Integer.toBinaryString(m);
        int jobLanguageN = jobLanguage.length();
        log.debug("{} /{} ", jobLanguage,jobLanguageN);


        if (jobLanguageN <= studentLanguageN && jobLanguageN != 5){
            log.debug("jobLanguageN不为5，而为:{}", jobLanguageN);
            log.debug("jobLanguageN的长度 <= studentLanguageN :{} <= {}", jobLanguageN,studentLanguageN);
            int diff = studentLanguageN - jobLanguageN;

            for ( i = jobLanguageN - 1; i >= 0 ; i--){
                log.debug("jobLanguage第{}位:{}", i +1,jobLanguage.substring(i, i+1 ));
                log.debug("studentLanguage{}位:{}", i +1,studentLanguage.substring(i+diff, i+1+diff ));
                if (jobLanguage.substring(i, i + 1 ).equals("1") && !studentLanguage.substring(i+diff, i+1+diff ).equals("1")){
                    log.debug("——————>jobLanguage第{}位:{}", i +1,jobLanguage.substring(i, i+1 ));
                    log.debug("——————>studentLanguage{}位:{}", i +1,studentLanguage.substring(i+diff, i + 1+diff ));
                    q = false;
                    break;
                }
            }
        }
        log.debug("{}", q);
    }

    @Test
    public void getStudentMatchResultTest(){
        studentService.getJobMatchResult();
        List<Job_match_result> job_match_results = jobMatchResultRepository.findAll();
        job_match_results.forEach(job_match_result -> {
            log.debug("{}/ {} ", job_match_result.getJmr_student().getS_name(),
                    job_match_result.getJmr_company().getC_name());
        });
    }
}
