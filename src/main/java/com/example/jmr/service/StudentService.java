package com.example.jmr.service;

import com.example.jmr.entity.*;
import com.example.jmr.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private JobMatchResultRepository jobMatchResultRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JmrBaseRepository jmrBaseRepository;

    private final static int EUMN_S_SEX_LENGTH = EumnWarehouse.S_SEX.values().length;
    private final static int EUMN_J_SEX_LENGTH = EumnWarehouse.J_SEX.values().length;
    private final static int EUMN_LEVEL_LENGTH = EumnWarehouse.C_LEVEL.values().length;
    private final static int EUMN_HISTORY_LENGTH = EumnWarehouse.E_HISTORY.values().length;
    private final static int EUMN_LANGUAGE_LENGTH = EumnWarehouse.F_LANGUAGE.values().length;
    private final static int EUMN_RANG_LENGTH = EumnWarehouse.S_RANGE.values().length;
    private final static int EUMN_CITY_LENGTH = EumnWarehouse.E_CITY.values().length;


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
    public Student getStudentByTelephone(String telephone){
        return studentRepository.getStudentByS_telephone(telephone).orElse(null);
    }

    /*------------简历信息（StudentResume）--------------
    -------检索：学生
    -------更新：学生
    -------创建：学生
    -------删除：学生
    --------------------------------------------------*/

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

    /*---------学生匹配的企业信息的各项数值（JmrBase）---------
    -------检索：管理员，学生，就业专员
    -------更新：数据库
    -------创建：数据库
    -------删除：
    --------------------------------------------------*/
    public Jmr_base addJmrBase(Jmr_base jmr_base){
        jmrBaseRepository.save(jmr_base);
        return jmr_base;
    }

    /*---------学生匹配的企业信息（JobMatchResult）---------
    -------检索：管理员，学生，就业专员
    -------更新：数据库
    -------创建：数据库
    -------删除：
    --------------------------------------------------*/
    public Job_match_result addJobMatchResult(Job_match_result job_match_result){
        jobMatchResultRepository.save(job_match_result);
        return job_match_result;
    }

    //验证性别是否符合要求
    //性别与要求一致或性别要求为无
    public boolean verifySex(EumnWarehouse.S_SEX studentSex,EumnWarehouse.J_SEX jobSex){
        boolean qualified = false;
        if (jobSex == EumnWarehouse.J_SEX.无要求 ||
                (jobSex ==  EumnWarehouse.J_SEX.男 && studentSex == EumnWarehouse.S_SEX.男) ||
                (jobSex ==  EumnWarehouse.J_SEX.女 && studentSex == EumnWarehouse.S_SEX.女))
            qualified = true;
        return qualified;
    }

    //验证性学校等级是否符合要求
    public int getSchoolLevelIndex(EumnWarehouse.C_LEVEL level){
        EumnWarehouse.C_LEVEL[] levels = EumnWarehouse.C_LEVEL.values();
        int index = EUMN_LEVEL_LENGTH;
        for(int i = 0;i < EUMN_LEVEL_LENGTH ; i++) {
            if (levels[i] == level) {
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifySchoolLevel(EumnWarehouse.C_LEVEL studentLevel, EumnWarehouse.C_LEVEL jobLevel){
        boolean qualified = false;
        if (getSchoolLevelIndex(studentLevel) <= getSchoolLevelIndex(jobLevel) ) qualified = true;
        return qualified;
    }

    //验证性学历等级是否符合要求
    public int getHistoryIndex(EumnWarehouse.E_HISTORY history){
        EumnWarehouse.E_HISTORY[] histories = EumnWarehouse.E_HISTORY.values();
        int index = EUMN_HISTORY_LENGTH;
        for(int i = 0;i < EUMN_HISTORY_LENGTH ; i++) {
            if (histories[i] == history){
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifyHistory(EumnWarehouse.E_HISTORY studentHistory,EumnWarehouse.E_HISTORY jobHistory){
        boolean qualified = false;
        if (getHistoryIndex(studentHistory) <= getHistoryIndex(jobHistory) ) qualified = true;
        return qualified;
    }

    //验证性外语等级是否符合要求
    public boolean verifyLanguage(int studentLanguage, int jobLanguage){
        boolean q = false;
        int i;

        String studentLanguageString = Integer.toBinaryString(studentLanguage);
        int studentLanguageN = studentLanguageString.length();

        String jobLanguageString = Integer.toBinaryString(jobLanguage);
        int jobLanguageN = jobLanguageString.length();

        //若job要求为"无条件"，则jobLanguage = 16，转为二进制后，长度为5，则符合条件
        //若job外语要求转为二进制后的长度 > 学生外语水平转为二进制后的长度，则不符合条件
        if (jobLanguageN == 5){
            q = true;
        }
        else if (jobLanguageN <= studentLanguageN){
            q =true;
            int diff = studentLanguageN - jobLanguageN;
            for ( i = jobLanguageN - 1; i >= 0 ; i--){
                if (jobLanguageString.substring(i, i + 1 ).equals("1") && !studentLanguageString.substring(i+diff, i+1+diff ).equals("1")){
                    q = false;
                    break;
                }
            }
        }
        return q;
    }

    //验证性期望薪资是否符合要求
    public int getRangeIndex(EumnWarehouse.S_RANGE range){
        EumnWarehouse.S_RANGE[] ranges = EumnWarehouse.S_RANGE.values();
        int index = EUMN_RANG_LENGTH;
        for(int i = 0;i < EUMN_RANG_LENGTH ; i++) {
            if (ranges[i] == range){
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifyRange(EumnWarehouse.S_RANGE studentRange,EumnWarehouse.S_RANGE jobRange){
        boolean qualified = false;
        if (getRangeIndex(studentRange) <= getRangeIndex(jobRange) ) {
            qualified = true;
        }
        return qualified;
    }

    //验证性就业意向地是否符合要求
    public int getCityIndex(EumnWarehouse.E_CITY city){
        EumnWarehouse.E_CITY[] cities = EumnWarehouse.E_CITY.values();
        int index = EUMN_CITY_LENGTH;
        for(int i = 0;i < EUMN_CITY_LENGTH ; i++) {
            if (cities[i] == city){
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifyCity(EumnWarehouse.E_CITY studentCity,EumnWarehouse.E_CITY jobCity){
        boolean qualified = false;
        if (getCityIndex(studentCity) <= getCityIndex(jobCity) ) qualified = true;
        return qualified;
    }

    //定时执行，匹配每一个岗位相对于每位一学生的条件符合值
    public void getJobMatchResult(){
        jobMatchResultRepository.deleteAll();
        jmrBaseRepository.deleteAll();

        List<Job> jobs = jobRepository.findAll();
        // 1.获取所有学生，并遍历
        List<Student> students = studentRepository.findAll();
        students.forEach(student -> {
            EumnWarehouse.S_SEX studentSex = student.getS_sex();
            EumnWarehouse.C_LEVEL studentLevel = student.getS_c_level();
            int studentProfessionId = student.getS_profession().getP_id();
            EumnWarehouse.E_HISTORY studentHistory = student.getS_e_history();
            int studentLanguages = student.getS_f_language();
            EumnWarehouse.S_RANGE studentRange = student.getS_s_range();
            int studentPositionId = student.getS_e_position().getP_id();
            EumnWarehouse.E_CITY studentCity = student.getS_e_city();

            // 2.计算每一个岗位相对于某位一学生的条件符合值
            jobs.forEach(job -> {
                Job_match_result job_match_result = new Job_match_result();
                Jmr_base jmr_base = new Jmr_base();
                Company company = job.getJ_company();
                int value = 0;

                job_match_result.setJmr_student(student);
                job_match_result.setJmr_company(company);
                job_match_result.setJmr_job(job);
                job_match_result.setJmr_value(value);

                EumnWarehouse.J_SEX jobSex = job.getJ_sex();
                EumnWarehouse.C_LEVEL jobLevel = job.getJ_c_level();
                int jobProfessionId = job.getJ_profession().getP_id();
                EumnWarehouse.E_HISTORY jobHistory = job.getJ_e_history();
                int jobLanguages = job.getJ_f_language();
                EumnWarehouse.S_RANGE jobRange = job.getJ_s_range();
                int jobPositionId = job.getJ_position().getP_id();
                EumnWarehouse.E_CITY jobCity = job.getJ_e_city();
                //3.分别计算8项待匹配项
                //3.1 性别
                if (verifySex(studentSex, jobSex)) {
                    jmr_base.setJmr_sex_value(1);
                    value = value + 1;
                }
                //3.2 学校层次
                if (verifySchoolLevel(studentLevel, jobLevel)) {
                    jmr_base.setJmr_level_value(1);
                    value = value + 1;
                }
                //3.3 专业
                if (studentProfessionId == jobProfessionId){
                    jmr_base.setJmr_profession_value(1);
                    value = value + 1;
                }
                //3.4 学历
                if (verifyHistory(studentHistory, jobHistory)){
                    jmr_base.setJmr_history_value(1);
                    value = value + 1;
                }
                //3.5 外语水平
                if (verifyLanguage(studentLanguages, jobLanguages)){
                    jmr_base.setJmr_language_value(1);
                    value = value + 1;
                }
                //3.6 期望薪资
                if (verifyRange(studentRange, jobRange)){
                    jmr_base.setJmr_range_value(1);
                    value = value + 1;
                };
                //3.7 期望岗位
                if (studentPositionId == jobPositionId){
                    jmr_base.setJmr_position_value(1);
                    value = value + 1;
                }
                //3.8 就业意向地
                if (verifyCity(studentCity, jobCity)){
                    jmr_base.setJmr_city_value(1);
                    value = value + 1;
                }
                //4.若匹配两项以上则存入数据库
                if (value >=2){
                    log.debug("{}",value);
                    job_match_result.setJmr_value(value);
                    jmrBaseRepository.save(jmr_base);
                    job_match_result.setJmr_base(jmr_base);
                    jobMatchResultRepository.save(job_match_result);
                }
            });
        });
    }
}
