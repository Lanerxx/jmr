package com.example.jmr.controller;

import com.example.jmr.entity.Company;
import com.example.jmr.entity.Student;
import com.example.jmr.service.CompanyService;
import com.example.jmr.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/register/")
@Slf4j
public class RegisterController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;

    @PostMapping("company")
    public Map registerCompany(@Valid @RequestBody Company company){
        Company c = new Company();
        if(company.getC_name()!=null && company.getC_password()!=null){
            if (companyService.getCompany(company.getC_name())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "该企业名已注册！");
            }
            c.setC_name(company.getC_name());
            c.setC_password(encoder.encode(company.getC_password()));
            companyService.addCompany(c);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "企业名和密码不能为空");
        }
        return Map.of(
                "company",c
        );
    }

    @PostMapping("student")
    public Map registerStudent(@Valid @RequestBody Student student){
        Student s = new Student();
        if(student.getS_name()!=null && student.getS_password()!=null){
            if (companyService.getCompany(student.getS_name())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "该学生名已注册！");
            }
            s.setS_name(student.getS_name());
            s.setS_password(encoder.encode(student.getS_password()));
            studentService.addStudent(s);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "姓名和密码不能为空");
        }
        return Map.of(
                "student",s
        );
    }
}
