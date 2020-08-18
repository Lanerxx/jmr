package com.example.jmr.service;

import com.example.jmr.component.EnumComponent;
import com.example.jmr.component.vo.CompanyJobVo;
import com.example.jmr.component.vo.StudentResumeVo;
import com.example.jmr.entity.*;
import com.example.jmr.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private JobMatchResultRepository jobMatchResultRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JmrBaseRepository jmrBaseRepository;
    @Autowired
    private CompanyJobRepository companyJobRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnumComponent enumComponent;

    /*--------------学生信息（Student）---------------
    -------检索：管理员，学生，就业专员
    -------更新：管理员，学生
    -------创建：管理员，学生
    -------删除：管理员，学生（注销）
    --------------------------------------------------*/
    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }
    public List<Student> addStudents(List<Student> students){
        return null;
    }
    public void deleteStudent(int sid){
        studentRepository.deleteById(sid);
    }
    public void deleteAllStudents(){
        studentRepository.deleteAll();
    }
    public Student updateStudent(Student student){
        studentRepository.save(student);
        return student;
    }
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    public Student getStudent(int sid){
        return studentRepository.findById(sid).orElse(null);
    }
    public List<Student> getStudentsByName(String name){
        return studentRepository.getStudentByS_name(name).orElse(new ArrayList<>());
    }
    public List<Student> getStudentByCollege(String college){
        return studentRepository.getStudentByS_college(college).orElse(new ArrayList<>());
    }
    public Student getStudentByTelephone(String telephone){
        return studentRepository.getStudentByS_telephone(telephone).orElse(null);
    }

    /*------------简历信息（StudentResume）--------------
    -------检索：学生
    -------更新：学生
    -------创建：学生
    -------删除：学生
    --------------------------------------------------*/
    public Resume addResume(Resume resume){
        resumeRepository.save(resume);
        return resume;
    }
    public void deleteResume(int rid){
        resumeRepository.deleteById(rid);
    }
    public void deleteAllResumes(){
        resumeRepository.deleteAll();
    }
    public Resume updateResume(Resume resume){
        resumeRepository.save(resume);
        return resume;
    }
    public List<Resume> getResumesByStudentId(int sid){
        return resumeRepository.getResumesByR_student(sid).orElse(new ArrayList<>());
    }
    public Resume getResume(int rid){
        return resumeRepository.findById(rid).orElse(null);
    }

    /*--------学生已发布简历信息（StudentResume）----------
    -------检索：管理员，学生，就业专员
    -------更新：管理员，学生
    -------创建：管理员，学生
    -------删除：管理员，学生
    --------------------------------------------------*/
    public Student_Resume addStudentResume(Student_Resume student_resume){
        studentResumeRepository.save(student_resume);
        return student_resume;
    }
    public void deleteStudentResume(int sid,int rid){
        studentResumeRepository.deleteStudent_resultsByStudentAndResume(sid, rid);
    }
    public Student_Resume getStudentResumeByStudentAndResume(int sid,int rid){
        return studentResumeRepository.getStudent_ResumeByStudentAndResume(sid, rid).orElse(null);
    }
    public List<Student_Resume> getStudentResumes(int sid){
        return studentResumeRepository.getStudentResumesByStudent(sid).orElse(new ArrayList<>());
    }
    public List<Student_Resume> getAllStudentResumes(){
        return studentResumeRepository.findAll();
    }
    public List<StudentResumeVo> getStudentResumeVoByStudent(int sid){
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        List<Student_Resume> student_resumes = studentService.getStudentResumes(sid);
        List<StudentResumeVo> studentResumeVos = new ArrayList<>();
        resumes.forEach(resume -> {
            student_resumes.forEach(student_resume -> {
                if (student_resume.getStudent_resume_pk().getResume().getR_id() ==
                        resume.getR_id()){
                    StudentResumeVo studentResumeVo = new StudentResumeVo();
                    studentResumeVo.setPosted(true);
                    studentResumeVo.setResume(resume);
                    studentResumeVos.add(studentResumeVo);
                }
            });
        });
        return studentResumeVos;
    }

    /*---------学生匹配的企业信息的各项数值（JmrBase）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public Jmr_base addJmrBase(Jmr_base jmr_base){
        jmrBaseRepository.save(jmr_base);
        return jmr_base;
    }

    /*---------学生匹配的企业信息（JobMatchResult）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public Job_match_result addJobMatchResult(Job_match_result job_match_result){
        jobMatchResultRepository.save(job_match_result);
        return job_match_result;
    }
    public void deleteStudentMatchResultByCompanyAndJob(int cid,int jid){
        jobMatchResultRepository.deleteJob_match_resultsByCompanyAndJob(cid, jid);
    }
    public List<Job_match_result> getAllJobMatchResults(){
        return jobMatchResultRepository.findAll();
    }
    public List<Job_match_result> getJobMatchResultsByStudent(int sid){
        return jobMatchResultRepository.getJob_match_resultsByStudent(sid).orElse(new ArrayList<>());
    }

    //定时执行，匹配每一个岗位相对于每位学生的条件符合值
    public void getJobMatchResult(){
        jobMatchResultRepository.deleteAll();
        jmrBaseRepository.deleteAll();

        //获取企业已发布的岗位
        List<Company_job> companyJobs = companyJobRepository.findAll();
        // 1.获取所有已发布简历的学生，并遍历
        List<Student_Resume> studentResumes = studentResumeRepository.findAll();
        studentResumes.forEach(studentResume -> {
            Student student = studentResume.getStudent_resume_pk().getStudent();
            EnumWarehouse.S_SEX studentSex = student.getS_sex();
            EnumWarehouse.C_LEVEL studentLevel = student.getS_c_level();
            int studentProfessionId = student.getS_profession().getP_id();
            EnumWarehouse.E_HISTORY studentHistory = student.getS_e_history();
            int studentLanguages = student.getS_f_language();
            EnumWarehouse.S_RANGE studentRange = student.getS_s_range();
            int studentPositionId = student.getS_e_position().getP_id();
            EnumWarehouse.E_CITY studentCity = student.getS_e_city();

            // 2.计算每一个岗位相对于某位一学生的条件符合值
            companyJobs.forEach(companyJob -> {
                Job job = companyJob.getCompany_job_pk().getJob();
                Job_match_result job_match_result = new Job_match_result();
                Jmr_base jmr_base = new Jmr_base();
                Company company = job.getJ_company();
                int value = 0;

                job_match_result.setJmr_student(student);
                job_match_result.setJmr_company(company);
                job_match_result.setJmr_job(job);
                job_match_result.setJmr_value(value);

                EnumWarehouse.J_SEX jobSex = job.getJ_sex();
                EnumWarehouse.C_LEVEL jobLevel = job.getJ_c_level();
                int jobProfessionId = job.getJ_profession().getP_id();
                EnumWarehouse.E_HISTORY jobHistory = job.getJ_e_history();
                int jobLanguages = job.getJ_f_language();
                EnumWarehouse.S_RANGE jobRange = job.getJ_s_range();
                int jobPositionId = job.getJ_position().getP_id();
                EnumWarehouse.E_CITY jobCity = job.getJ_e_city();
                //3.分别计算8项待匹配项
                //3.1 性别
                if (enumComponent.verifySex(studentSex, jobSex)) {
                    jmr_base.setJmr_sex_value(1);
                    value = value + 1;
                }
                //3.2 学校层次
                if (enumComponent.verifySchoolLevel(studentLevel, jobLevel)) {
                    jmr_base.setJmr_level_value(1);
                    value = value + 1;
                }
                //3.3 专业
                if (studentProfessionId == jobProfessionId){
                    jmr_base.setJmr_profession_value(1);
                    value = value + 1;
                }
                //3.4 学历
                if (enumComponent.verifyHistory(studentHistory, jobHistory)){
                    jmr_base.setJmr_history_value(1);
                    value = value + 1;
                }
                //3.5 外语水平
                if (enumComponent.verifyLanguage(studentLanguages, jobLanguages)){
                    jmr_base.setJmr_language_value(1);
                    value = value + 1;
                }
                //3.6 期望薪资
                if (enumComponent.verifyRange(studentRange, jobRange)){
                    jmr_base.setJmr_range_value(1);
                    value = value + 1;
                };
                //3.7 期望岗位
                if (studentPositionId == jobPositionId){
                    jmr_base.setJmr_position_value(1);
                    value = value + 1;
                }
                //3.8 就业意向地
                if (enumComponent.verifyCity(studentCity, jobCity)){
                    jmr_base.setJmr_city_value(1);
                    value = value + 1;
                }
                //4.若匹配两项以上则存入数据库
                if (value >=2){
                    job_match_result.setJmr_value(value);
                    jmrBaseRepository.save(jmr_base);
                    job_match_result.setJmr_base(jmr_base);
                    jobMatchResultRepository.save(job_match_result);
                }
            });
        });
    }

    //及时执行，根据用户要求匹配学生
    public List<Job_match_result> getJobMatchResult(Map<String,Integer> focus, int sid){
        List<Job_match_result> jobMatchResults = studentService.getJobMatchResultsByStudent(sid);
        List<Job_match_result> jobMatchResultsFocus = new ArrayList<>();
        log.debug("{}", focus.toString());
        jobMatchResults.forEach(jobMatchResult -> {
            int value= 0;
            Job_match_result jmr = new Job_match_result();
            Jmr_base jb = jobMatchResult.getJmr_base();
            if (focus.get("sex") == 1 && jb.getJmr_sex_value() == 1) {
                value = value + 1;
                log.debug("sex{}", value);

            }
            if (focus.get("level") == 1 && jb.getJmr_level_value() == 1){
                value = value + 1;
                log.debug("level{}", value);

            }
            if (focus.get("profession") == 1 && jb.getJmr_profession_value() == 1) {
                value = value + 1;
                log.debug("profession{}", value);

            }
            if (focus.get("history") == 1 && jb.getJmr_history_value() == 1){
                value = value + 1;
                log.debug("history{}", value);

            }
            if (focus.get("language") == 1 && jb.getJmr_language_value() == 1){
                value = value + 1;
                log.debug("language{}", value);

            }
            if (focus.get("range") == 1 && jb.getJmr_range_value() == 1){
                value = value + 1;
                log.debug("range{}", value);

            }
            if (focus.get("position") == 1 && jb.getJmr_position_value() == 1){
                value = value + 1;
                log.debug("position{}", value);

            }
            if (focus.get("city") == 1 && jb.getJmr_city_value() == 1){
                value = value + 1;
                log.debug("{}", value);

            }
            log.debug("{}", value);
            jobMatchResult.setJmr_value(value);
            jobMatchResultsFocus.add(jobMatchResult);
        });
        return jobMatchResultsFocus;
    }
}
