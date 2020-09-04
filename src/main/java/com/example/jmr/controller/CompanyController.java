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
import java.util.Set;

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

    @PatchMapping("information")
    public Map updateCompanyInformation(@RequestBody Company company){
        Company c = companyService.getCompany(requestComponent.getUid());
        c.setC_name(company.getC_name());
        c.setC_s_code(company.getC_s_code());
        c.setC_description(company.getC_description());
        c.setC_contact(company.getC_contact());
        c.setC_telephone(company.getC_telephone());
        c.setC_email(company.getC_email());
        companyService.updateCompany(c);
        return Map.of(
                "company",c
        );
    }
    @GetMapping("jobs")
    public Map getJobs(){
        log.debug("{}", requestComponent.getUid());
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        List<String> positionsName = positionService.listPositionsName();
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "companyJobs",companyJobs,
                "positions",positionsName,
                "professionsMClass",professionsMClass
                );
    }

    @PostMapping("job")
    public Map addJob(@Valid @RequestBody Job job){
        int cid = requestComponent.getUid();
        Company company = companyService.getCompany(cid);
        job.setJ_company(company);
        Position po = positionService.getPosition(job.getJ_position().getP_name());
        List<Profession> prs = professionService.getProfessionsByMClass(job.getJ_profession().getP_m_class());
        job.setJ_profession(prs.get(0));
        job.setJ_position(po);
        log.debug("{}", job.getJ_company().getC_name());
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        companyService.addJob(job);
        Company_job companyJob = new Company_job();
        companyJob.setCj_focus(false);
        Company_job_PK company_job_pk = new Company_job_PK();
        company_job_pk.setCompany(company);
        company_job_pk.setJob(job);
        companyJob.setCompany_job_pk(company_job_pk);
        log.debug("{}", companyJob.getCompany_job_pk());
        companyService.addCompanyJob(companyJob);
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobs",companyJobs
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
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @PatchMapping("job")
    public Map updateJob(@RequestBody Job job){
        job.setJ_company(companyService.getCompany(requestComponent.getUid()));
        Position po = positionService.getPosition(job.getJ_position().getP_name());
        List<Profession> prs = professionService.getProfessionsByMClass(job.getJ_profession().getP_m_class());
        job.setJ_profession(prs.get(0));
        job.setJ_position(po);
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        companyService.updateJob(job);
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @PostMapping("companyJob")
    public Map addCompanyJob(@Valid @RequestBody Company_job companyJob){
        int jid = companyJob.getCompany_job_pk().getJob().getJ_id();
        Job job = companyService.getJob(jid);
        if (job == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想发布的岗位不存在");
        }
        Company_job_PK companyJobPk = new Company_job_PK();
        companyJobPk.setJob(companyService.getJob(jid));
        companyJobPk.setCompany(companyService.getCompany(requestComponent.getUid()));

        Company_job cj = new Company_job();
        cj.setCompany_job_pk(companyJobPk);
        cj.setCj_focus(companyJob.isCj_focus());
        companyService.updateCompanyJob(cj);
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @PatchMapping("companyJob/{jid}")
    public Map updateCompanyJob(@PathVariable int jid){
        int cid = requestComponent.getUid();
        if (companyService.getCompanyJobByCompanyAndJob(cid, jid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想回收的岗位不存在");
        }
        studentService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        companyService.deleteStudentMatchResultByCompanyAndJob(cid, jid);
        Company_job companyJob = companyService.getCompanyJobByCompanyAndJob(cid, jid);
        companyJob.setCj_focus(false);
        companyService.updateCompanyJob(companyJob);
        List<Company_job> companyJobs = companyService.getCompanyJobsByCompany(requestComponent.getUid());
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @GetMapping("smr/{cjid}")
    public Map getSmr(@PathVariable int cjid){
        Company_job company_job = companyService.getCompanyJobByCompanyAndJob(requestComponent.getUid(), cjid);
        if (company_job == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想匹配的岗位不存在或尚未发布");
        }
        List<Student_match_result> student_match_results = companyService.getStudentMatchResultByJob(cjid);
        if (student_match_results.size() == 0 ){
            companyService.getStudentMatchResultByJob(company_job, cjid);
            student_match_results = companyService.getStudentMatchResultByJob(cjid);
        }
        return Map.of(
                "studentMatchResults", student_match_results
        );
    }

    @PostMapping("smr/{jid}")
    public Map getJmr(@PathVariable int jid,@RequestBody Map<String,Integer> focus){
        List<Student_match_result> studentMatchResults = companyService.getStudentMatchResult(focus, jid);
        return Map.of(
                "studentMatchResults", studentMatchResults
        );
    }


}
