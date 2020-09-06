package com.example.jmr.controller;

import com.example.jmr.component.CheckIsNullComponent;
import com.example.jmr.component.EncryptComponent;
import com.example.jmr.component.RequestComponent;
import com.example.jmr.component.vo.CompanyJobVo;
import com.example.jmr.component.vo.PasswordVo;
import com.example.jmr.component.vo.StudentResumeVo;
import com.example.jmr.entity.*;
import com.example.jmr.service.CompanyService;
import com.example.jmr.service.PositionService;
import com.example.jmr.service.ProfessionService;
import com.example.jmr.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/student/")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("index")
    public Map getIndex(){
        log.debug("sfsdsdf");
        Student student = studentService.getStudent(requestComponent.getUid());
        log.debug("{}",student.getS_name());
        List<String> positionsName = positionService.listPositionsName();
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "student",student,
                "positions",positionsName,
                "professionsMClass",professionsMClass
        );
    }

    @PatchMapping("information")
    public Map updateStudentInformation(@RequestBody Student student){
        Student s = studentService.getStudent(requestComponent.getUid());
        if (s == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该学生不存在！");
        }
        if (student.getS_name() == null ||
                student.getS_sex() == null || student.getS_birthday() == null||
                student.getS_college() == null || student.getS_c_level() == null ||
                student.getS_profession().getP_s_class() == null ||
                student.getS_e_history() == null || student.getS_n_province() == null||
                student.getS_n_city() == null  ||
                student.getS_s_range() == null || student.getS_e_position() == null ||
                student.getS_e_city() == null || student.getS_g_time() == null ||
                student.getS_telephone() == null || student.getS_email() == null ||
                student.getS_if_work() == null
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的必填信息！");
        }
        if (student.getS_if_work().equals(EnumWarehouse.IF_WORK.已就业)){
            if (student.getS_w_city() == null || student.getS_company() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您还未填写已就业的城市或企业！");
            }
        }
        Profession profession = professionService.getProfessionBySClass(student.getS_profession().getP_s_class());
        if (profession == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的专业错误！");
        }
        Position position = positionService.getPosition(student.getS_e_position().getP_name());
        if (position == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的岗位错误！");
        }
        s.setS_name(student.getS_name());
        if (student.getS_id_card() != null) s.setS_id_card(student.getS_id_card());
        s.setS_sex(student.getS_sex());
        s.setS_birthday(student.getS_birthday());
        s.setS_college(student.getS_college());
        s.setS_c_level(student.getS_c_level());
        s.setS_profession(profession);
        s.setS_e_history(student.getS_e_history());
        s.setS_n_province(student.getS_n_province());
        s.setS_n_city(student.getS_n_city());
        if (student.getS_f_language()!=0) s.setS_f_language(student.getS_f_language());
        s.setS_s_range(student.getS_s_range());
        s.setS_e_position(position);
        s.setS_e_city(student.getS_e_city());
        s.setS_g_time(student.getS_g_time());
        s.setS_telephone(student.getS_telephone());
        s.setS_email(student.getS_email());
        s.setS_s_attachment(student.getS_s_attachment());
        s.setS_if_work(student.getS_if_work());
        if (student.getS_if_work().equals(EnumWarehouse.IF_WORK.已就业)){
            s.setS_w_city(student.getS_w_city());
            s.setS_company(student.getS_company());
        }else {
            s.setS_w_city(null);
            s.setS_company(null);
        }
        studentService.updateStudent(s);
        return Map.of(
                "student",s
        );
    }

    @PatchMapping("password")
    public Map updatePassword(@RequestBody PasswordVo passwordVo){
        log.debug(passwordVo.getOldPassword());
        Student s = studentService.getStudent(requestComponent.getUid());
        if (!encoder.matches(passwordVo.getOldPassword(), s.getS_password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您输入旧密码错误");
        }else {
            s.setS_password(encoder.encode(passwordVo.getNewPassword()));
        }
        studentService.updateStudent(s);
        return Map.of(
                "student",s
        );
    }

    @GetMapping("resumes")
    public Map getResumes(){
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(requestComponent.getUid());
        studentResumeVos.forEach(studentResumeVo -> {
            log.debug("{} / {}", studentResumeVo.getResume().getR_career(),studentResumeVo.getResume().getR_m_course());
        });
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @PostMapping("resume")
    public Map addResume(@Valid @RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        resume.setR_student(student);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        studentService.addResume(resume);
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(sid);
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @DeleteMapping("resume/{rid}")
    public Map deleteResume(@PathVariable int rid){
        if (studentService.getResume(rid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的岗位不存在");
        }
        int sid = requestComponent.getUid();
        companyService.deleteStudentMatchResultByResume(rid);
        studentService.deleteStudentResume(sid, rid);
        studentService.deleteResume(rid);
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(sid);
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @PatchMapping("resume")
    public Map updateResume(@RequestBody Resume resume){
        Student s = studentService.getStudent(requestComponent.getUid());
        resume.setR_student(s);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        if (studentService.getResume(resume.getR_id()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想修改的简历不存在");

        }
        studentService.updateResume(resume);
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(requestComponent.getUid());
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @PostMapping("studentResume")
    public Map addStudentResume(@Valid @RequestBody Student_Resume studentResume){
        log.debug("{}", studentResume.getStudent_resume_pk().getResume().getR_id());
        int rid = studentResume.getStudent_resume_pk().getResume().getR_id();
        Resume r = studentService.getResume(rid);
        if (r == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想发布的简历不存在");
        }
        Student_Resume sr = new Student_Resume();
        Student_resume_PK studentResumePk = new Student_resume_PK();
        Student s = studentService.getStudent(requestComponent.getUid());
        studentResumePk.setStudent(s);
        studentResumePk.setResume(r);
        sr.setStudent_resume_pk(studentResumePk);
        studentService.addStudentResume(sr);
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(requestComponent.getUid());
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @DeleteMapping("studentResume/{rid}")
    public Map deleteStudentResume(@PathVariable int rid){
        int sid = requestComponent.getUid();
        if (studentService.getStudentResumeByStudentAndResume(sid, rid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想回收的简历不存在");
        }
        companyService.deleteStudentMatchResultByStudentAndResume(sid, rid);
        studentService.deleteStudentResume(sid, rid);
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(sid);
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @GetMapping("jmr")
    public Map getJmr(){
        List<Job_match_result> job_match_results = studentService.getJobMatchResultsByStudent(requestComponent.getUid());
        if (job_match_results.size() == 0){
            Student student = studentService.getStudent(requestComponent.getUid());
            job_match_results = studentService.getJobMatchResultByStudent(student);
        }
        return Map.of(
                "jobMatchResults", job_match_results
        );
    }

    @PostMapping("jmr")
    public Map getJmr(@RequestBody Map<String,Integer> focus){
        List<Job_match_result> jobMatchResults = studentService.getJobMatchResult(focus, requestComponent.getUid());
        return Map.of(
                "jobMatchResults", jobMatchResults
        );
    }

}
