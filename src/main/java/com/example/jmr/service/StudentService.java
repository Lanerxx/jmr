package com.example.jmr.service;

import com.example.jmr.entity.Job_match_result;
import com.example.jmr.entity.Student;
import com.example.jmr.entity.Student_Resume;
import com.example.jmr.repository.JobMatchResultRepository;
import com.example.jmr.repository.StudentRepository;
import com.example.jmr.repository.StudentResumeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
