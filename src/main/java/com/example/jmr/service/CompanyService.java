package com.example.jmr.service;

import com.example.jmr.component.EnumComponent;
import com.example.jmr.entity.*;
import com.example.jmr.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private SmrBaseRepository smrBaseRepository;
    @Autowired
    private StudentMatchResultRepository studentMatchResultRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private EnumComponent enumComponent;


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
    public void deleteCompany(int cid){
        companyRepository.deleteById(cid);
    }
    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }
    public Company updateCompany(Company company){
        companyRepository.save(company);
        return company;
    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    public List<Company> getCompaniesByName(String name){
        return companyRepository.getCompanyByC_name(name).orElse(new ArrayList<>());
    }
    public Company getCompanyByCode(String code){
        return companyRepository.getCompanyByC_s_code(code).orElse(null);
    }
    public Company getCompanyByTelephone(String telephone){
        return companyRepository.getCompanyByC_telephone(telephone).orElse(null);
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
    public void deleteJob(int jid){
        jobRepository.deleteById(jid);
    }
    public void deleteAllJobs(){
        jobRepository.deleteAll();
    }
    public Job updateJob(Job job){
        jobRepository.save(job);
        return job;
    }
    public Job getJob(int jid){
        return jobRepository.findById(jid).orElse(null);
    }
    public List<Job> getJobsByCompany(int cid){
        return jobRepository.getJobsByCompany(cid).orElse(new ArrayList<>());
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
    public void deleteCompanyJob(int cjid){
        companyJobRepository.deleteById(cjid);
    }
    public void deleteAllCompanyJobs(){
        companyJobRepository.deleteAll();
    }
    public List<Company_job> getAllCompanyJobs(){
        return companyJobRepository.findAll();
    }
    public List<Company_job> getCompanyJobs(String positionName){
        return companyJobRepository.getCompany_jobsByPositionName(positionName).orElse(new ArrayList<>());
    }
    public List<Company_job> getCompanyJobs(int cid){
        return companyJobRepository.getCompany_jobsByCompany(cid).orElse(new ArrayList<>());
    }

    /*---------企业匹配的学生信息的各项数值（JmrBase）---------
    -------检索：管理员，公司，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public Smr_base addSmrBase(Smr_base smr_base){
        smrBaseRepository.save(smr_base);
        return smr_base;
    }

    /*------企业匹配的学生信息（StudentMatchResult）-------
    -------检索：管理员，公司，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public Student_match_result addStudentMatchResult(Student_match_result student_match_result){
        studentMatchResultRepository.save(student_match_result);
        return student_match_result;
    }
    public List<Student_match_result> getAllStudentMatchResult(){
        return studentMatchResultRepository.findAll();
    }

    //定时执行，匹配每一个学生相对于每个岗位的条件符合值
    public void getStudentMatchResult(){
        studentMatchResultRepository.deleteAll();
        smrBaseRepository.deleteAll();

        //获取学生已发布的简历
        List<Student_Resume> studentResumes = studentResumeRepository.findAll();
        // 1.获取所有企业已发布的岗位，并遍历
        List<Company_job> companyJobs = companyJobRepository.findAll();
        companyJobs.forEach(companyJob -> {
            Job job = companyJob.getCompany_job_pk().getJob();
            EnumWarehouse.J_SEX jobSex = job.getJ_sex();
            EnumWarehouse.C_LEVEL jobLevel = job.getJ_c_level();
            int jobProfessionId = job.getJ_profession().getP_id();
            EnumWarehouse.E_HISTORY jobHistory = job.getJ_e_history();
            int jobLanguages = job.getJ_f_language();
            EnumWarehouse.S_RANGE jobRange = job.getJ_s_range();
            int jobPositionId = job.getJ_position().getP_id();
            EnumWarehouse.E_CITY jobCity = job.getJ_e_city();

            Company company = job.getJ_company();

            // 2.计算每一个岗位相对于某位一学生的条件符合值
            studentResumes.forEach(studentResume -> {
                Student student = studentResume.getStudent_resume_pk().getStudent();
                Resume resume = studentResume.getStudent_resume_pk().getResume();
                Student_match_result student_match_result = new Student_match_result();
                Smr_base smr_base = new Smr_base();
                int value = 0;

                student_match_result.setSmr_student(student);
                student_match_result.setSmr_company(company);
                student_match_result.setSmr_resume(resume);
                student_match_result.setSmr_value(value);

                EnumWarehouse.S_SEX studentSex = student.getS_sex();
                EnumWarehouse.C_LEVEL studentLevel = student.getS_c_level();
                int studentProfessionId = student.getS_profession().getP_id();
                EnumWarehouse.E_HISTORY studentHistory = student.getS_e_history();
                int studentLanguages = student.getS_f_language();
                EnumWarehouse.S_RANGE studentRange = student.getS_s_range();
                int studentPositionId = student.getS_e_position().getP_id();
                EnumWarehouse.E_CITY studentCity = student.getS_e_city();
                //3.分别计算8项待匹配项
                //3.1 性别
                if (enumComponent.verifySex(studentSex, jobSex)) {
                    smr_base.setSmr_sex_value(1);
                    value = value + 1;
                }
                //3.2 学校层次
                if (enumComponent.verifySchoolLevel(studentLevel, jobLevel)) {
                    smr_base.setSmr_level_value(1);
                    value = value + 1;
                }
                //3.3 专业
                if (studentProfessionId == jobProfessionId){
                    smr_base.setSmr_profession_value(1);
                    value = value + 1;
                }
                //3.4 学历
                if (enumComponent.verifyHistory(studentHistory, jobHistory)){
                    smr_base.setSmr_history_value(1);
                    value = value + 1;
                }
                //3.5 外语水平
                if (enumComponent.verifyLanguage(studentLanguages, jobLanguages)){
                    smr_base.setSmr_language_value(1);
                    value = value + 1;
                }
                //3.6 期望薪资
                if (enumComponent.verifyRange(studentRange, jobRange)){
                    smr_base.setSmr_range_value(1);
                    value = value + 1;
                };
                //3.7 期望岗位
                if (studentPositionId == jobPositionId){
                    smr_base.setSmr_position_value(1);
                    value = value + 1;
                }
                //3.8 就业意向地
                if (enumComponent.verifyCity(studentCity, jobCity)){
                    smr_base.setSmr_city_value(1);
                    value = value + 1;
                }
                //4.若匹配两项以上则存入数据库
                if (value >=2){
                    student_match_result.setSmr_value(value);
                    smrBaseRepository.save(smr_base);
                    student_match_result.setSmr_base(smr_base);
                    studentMatchResultRepository.save(student_match_result);
                }
            });
        });
    }

}
