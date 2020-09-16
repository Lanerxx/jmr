package com.example.jmr.controller;

import com.example.jmr.component.CheckIsNullComponent;
import com.example.jmr.component.RequestComponent;
import com.example.jmr.component.vo.ProfessionMClassVo;
import com.example.jmr.component.vo.StudentResumeVo;
import com.example.jmr.entity.*;
import com.example.jmr.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/generalAdmin/")
@Slf4j
public class GeneralAdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JobDirectorService jobDirectorService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("index")
    public Map getIndex(){
        Admin admin = adminService.getAdmin(requestComponent.getUid());
        return Map.of(
                "admin",admin
        );
    }

    @GetMapping("positions")
    public Map getPosition(){
        List<Position> positions = positionService.getAllPositions();
        return Map.of(
                "positions",positions
        );
    }

    @GetMapping("professionMClassVo")
    public Map getProfessionsMClassVo(){
        List<ProfessionMClassVo> professionMClassVos = professionService.getProfessionsMClassVo();
        return Map.of(
                "professionMClassVos",professionMClassVos
        );
    }

    @GetMapping("professionMClass")
    public Map getProfessionMClass(){
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "professionsMClass",professionsMClass
        );
    }

    @PostMapping("professionsSClass")
    public Map getIndex(@Valid @RequestBody Profession profession){
        Profession p = professionService.getProfession(profession.getP_id());
        List<Profession> professionsSClass = professionService.getProfessionsByMClass(p.getP_m_class());
        professionsSClass.forEach(profession1 -> {
        });
        return Map.of(
                "professionsSClass",professionsSClass
        );
    }

    @GetMapping("students")
    public Map getStudents(){
        List<Student> students = studentService.getAllStudents();
        return Map.of(
                "students",students
        );
    }

    @GetMapping("student/{sid}")
    public Map getStudent(@PathVariable int sid){
        Student student = studentService.getStudent(sid);
        return Map.of(
                "student",student
        );
    }

    @GetMapping("studentResume/{sid}")
    public Map getStudentResumes(@PathVariable int sid){
        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(sid);
        return Map.of(
                "studentResumeVos",studentResumeVos
        );
    }

    @GetMapping("jmr/{sid}")
    public Map getJmr(@PathVariable int sid){
        List<Job_match_result> jobMatchResults = studentService.getJobMatchResultsByStudent(sid);
        return Map.of(
                "jobMatchResults",jobMatchResults
        );
    }

    @GetMapping("companies")
    public Map getCompanies(){
        List<Company> companies = companyService.getAllCompanies();
        return Map.of(
                "companies",companies
        );
    }

    @GetMapping("company/{cid}")
    public Map getCompany(@PathVariable int cid){
        Company company = companyService.getCompany(cid);
        return Map.of(
                "company",company
        );
    }

    @GetMapping("companyJob/{cid}")
    public Map getCompanyJobs(@PathVariable int cid){
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(cid);
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @GetMapping("smr/{cid}")
    public Map getSmr(@PathVariable int cid){
        List<Student_match_result> studentMatchResults = companyService.getStudentMatchResultByCompany(cid);
        return Map.of(
                "studentMatchResults",studentMatchResults
        );
    }

    @GetMapping("jobDirector")
    public Map getJobDirectors(){
        List<Job_director> jobDirectors = jobDirectorService.getAllJobDirectors();
        return Map.of(
                "jobDirectors",jobDirectors
        );
    }

}
