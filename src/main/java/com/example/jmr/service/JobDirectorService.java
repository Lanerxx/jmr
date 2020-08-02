package com.example.jmr.service;

import com.example.jmr.entity.Job_director;
import com.example.jmr.repository.JobDirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class JobDirectorService {
    @Autowired
    private JobDirectorRepository jobDirectorRepository;

    /*-------------就业专员信息（JobDirector）------------
    -------检索：管理员，就业专员
    -------更新：管理员，就业专员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Job_director addJobDirector(Job_director job_director){
        jobDirectorRepository.save(job_director);
        return job_director;
    }
    public Job_director getJobDirectorByTelephone(String telephone){
        return jobDirectorRepository.getJobDirectorByJd_telephone(telephone).orElse(null);
    }

}
