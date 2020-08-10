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
@RequestMapping("/api/company/")
@Slf4j
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private StudentService studentService;

    @GetMapping("index")
    public Map getIndex(){
        Company company = companyService.getCompany(requestComponent.getUid());
        return Map.of(
                "company",company
        );
    }

    @GetMapping("jobs")
    public Map getJobs(){
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        List<Position> positions = positionService.getAllPositions();
        List<Profession> professions = professionService.getAllProfessions();
        return Map.of(
                "companyJobVos",companyJobVos,
                "positions",positions,
                "professions",professions
                );
    }

    @PostMapping("job")
    public Map addJob(@Valid @RequestBody Job job){
        int cid = requestComponent.getUid();
        Company company = companyService.getCompany(cid);
        job.setJ_company(company);
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        companyService.addJob(job);
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobVos",companyJobVos
        );
    }

    @DeleteMapping("job/{jid}")
    public Map deleteJob(@PathVariable int jid){
        if (companyService.getJob(jid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的岗位不存在");
        }
        int cid = requestComponent.getUid();
        studentService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        companyService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        companyService.deleteCompanyJob(cid,jid);
        companyService.deleteJob(jid);
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobVos",companyJobVos
        );
    }

    @PatchMapping("job")
    public Map updateJob(@RequestBody Job job){
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        companyService.updateJob(job);
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobVos",companyJobVos
        );
    }

    @PostMapping("companyJob")
    public Map addCompanyJob(@Valid @RequestBody Company_job companyJob){
        if(checkIsNullComponent.objCheckIsNull(companyJob)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        companyService.addCompanyJob(companyJob);
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobVos",companyJobVos
        );
    }

    @DeleteMapping("companyJob/{cid}/{jid}")
    public Map deleteCompanyJob(@PathVariable int cid,@PathVariable int jid){
        if (companyService.getCompanyJobByCompanyAndJob(cid, jid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的已发布岗位不存在");
        }
        studentService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        companyService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        companyService.deleteCompanyJob(cid,jid);
        List<CompanyJobVo> companyJobVos = companyService.getCompanyJobsVoByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobVos",companyJobVos
        );
    }

    @GetMapping("smr/{jid}")
    public Map getSmr(@PathVariable int jid){
        List<Student_match_result> student_match_results = companyService.getStudentMatchResultByJob(jid);
        return Map.of(
                "studentMatchResults", student_match_results
        );
    }


}
