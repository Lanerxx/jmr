package com.example.jmr.service;

import com.example.jmr.entity.Job_director;
import com.example.jmr.repository.JobDirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public void deleteJobDirector(int jbid){
        jobDirectorRepository.deleteById(jbid);
    }
    public void deleteAllJobDirectors(){
        jobDirectorRepository.deleteAll();
    }
    public Job_director updateJobDirector(Job_director job_director){
        jobDirectorRepository.save(job_director);
        return job_director;
    }
    public List<Job_director> getAllJobDirectors(){
        return jobDirectorRepository.findAll();
    }
    public Job_director getJobDirector(int jdid){
        return jobDirectorRepository.findById(jdid).orElse(null);
    }
    public List<Job_director> getJobDirectorsByName(String name){
        return jobDirectorRepository.getJobDirectorByJd_name(name).orElse(new ArrayList<>());
    }
    public Job_director getJobDirectorByTelephone(String telephone){
        return jobDirectorRepository.getJobDirectorByJd_telephone(telephone).orElse(null);
    }


}
