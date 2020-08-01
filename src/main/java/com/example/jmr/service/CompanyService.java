package com.example.jmr.service;

import com.example.jmr.entity.Company;
import com.example.jmr.entity.Company_job;
import com.example.jmr.entity.Job;
import com.example.jmr.repository.CompanyJobRepository;
import com.example.jmr.repository.CompanyRepository;
import com.example.jmr.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyJobRepository companyJobRepository;


    /*---------------企业信息（Company）-----------------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司（注册-审核）
    -------删除：管理员，公司
    --------------------------------------------------*/
    public Company addCompany(Company company){
        companyRepository.save(company);
        return company;
    }
    public Company getCompany(String name){
        return companyRepository.findByC_name(name).orElse(null);
    }

    /*-----------------职位信息（Job）-------------------
    -------检索：公司
    -------更新：公司
    -------创建：公司
    -------删除：公司
    --------------------------------------------------*/
    public Job addJob(Job job){
        jobRepository.save(job);
        return job;
    }

    /*----------企业已发布职位信息（CompanyJob）-----------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司
    -------删除：管理员，公司
    --------------------------------------------------*/
    public Company_job addCompanyJob(Company_job company_job){
        companyJobRepository.save(company_job);
        return company_job;
    }

    /*------企业匹配的学生信息（StudentMatchResult）-------
    -------检索：管理员，公司，就业专员
    -------更新：数据库
    -------创建：数据库
    -------删除：
    --------------------------------------------------*/




}
