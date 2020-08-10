package com.example.jmr.controller;

import com.example.jmr.component.CheckIsNullComponent;
import com.example.jmr.component.RequestComponent;
import com.example.jmr.component.vo.CompanyJobVo;
import com.example.jmr.entity.*;
import com.example.jmr.service.CompanyService;
import com.example.jmr.service.PositionService;
import com.example.jmr.service.ProfessionService;
import com.example.jmr.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping("index")
    public Map getIndex(){
        Student student = studentService.getStudent(requestComponent.getUid());
        return Map.of(
                "student",student
        );
    }

    @GetMapping("resumes")
    public Map getResumes(){
//        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        List<Position> positions = positionService.getAllPositions();
        List<Profession> professions = professionService.getAllProfessions();
        return Map.of(
//                "companyJobVos",companyJobVos,
                "positions",positions,
                "professions",professions
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
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("resume/{rid}")
    public Map deleteResume(@PathVariable int rid){
        if (studentService.getResume(rid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的岗位不存在");
        }
        int sid = requestComponent.getUid();
        companyService.deleteStudentMatchResultByStudentAndResume(sid, rid);
        studentService.deleteStudentResume(sid, rid);
        studentService.deleteResume(rid);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @PatchMapping("resume")
    public Map updateResume(@RequestBody Resume resume){
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        studentService.updateResume(resume);
        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }

    @PostMapping("studentResume")
    public Map addStudentResume(@Valid @RequestBody Student_Resume student_resume){
        if(checkIsNullComponent.objCheckIsNull(student_resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        studentService.addStudentResume(student_resume);
        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("studentResume/{sid}/{rid}")
    public Map deleteStudentResume(@PathVariable int sid,@PathVariable int rid){
        if (studentService.getStudentResumeByStudentAndResume(sid, rid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的已发布岗位不存在");
        }

        companyService.deleteStudentMatchResultByStudentAndResume(sid, rid);
        studentService.deleteStudentResume(sid, rid);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @GetMapping("jmr")
    public Map getJmr(){
        List<Job_match_result> job_match_results = studentService.getJobMatchResultsByStudent(requestComponent.getUid());
        return Map.of(
                "jobMatchResults", job_match_results
        );
    }

}
